package mission.stepDefs;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mission.utils.ApiBase;
import mission.utils.ApiConstants;

public class APIStepDefinition extends ApiBase {

    private Response lastResponse;
    private Set<Integer> userIds = new HashSet<>();

    // Helper to create a default request specification
    private RequestSpecification defaultRequest() {
        return given()
                .header("User-Agent", "Mozilla/5.0")
                .header("Authorization", "Bearer <session_token>")
                .contentType(ContentType.JSON);
    }

 // ---------------- Login (Reusable) ----------------
    private void performLogin(DataTable credentials) {
        Map<String, String> creds =
                credentials.asMaps(String.class, String.class).get(0);

        Map<String, String> payload = new HashMap<>();

        String email = creds.get("Email");
        if (email != null && !email.trim().isEmpty()) {
            payload.put("email", email.trim());
        }

        String password = creds.get("Password");
        if (password != null && !password.trim().isEmpty()
                && !password.equalsIgnoreCase("[empty]")) {
            payload.put("password", password.trim());
        }

        lastResponse = defaultRequest()
                .body(payload)
                .when()
                .post(ApiConstants.LOGIN);

        System.out.println("Login payload: " + payload);
        System.out.println("Response Body: " + lastResponse.getBody().asString());
    }
    // ---------------- List Users ----------------
    @Given("I get the default list of users for on 1st page")
    public void get_users_first_page() {
        lastResponse = defaultRequest()
                .when()
                .get(ApiConstants.USERS + "?page=1");

        System.out.println("Response Body: " + lastResponse.getBody().asString());
        assertEquals(lastResponse.statusCode(), 200, "Expected status code 200");
    }

    @When("I get the list of all users within every page")
    public void get_all_users() {
        int totalPages = lastResponse.jsonPath().getInt("total_pages");

        for (int i = 1; i <= totalPages; i++) {
            Response response = defaultRequest()
                    .when()
                    .get(ApiConstants.USERS + "?page=" + i);

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch users on page " + i);
            }

            List<Integer> ids = response.jsonPath().getList("data.id");
            userIds.addAll(ids);
        }
    }

    @Then("I should see total users count equals the number of user ids")
    public void verify_total_users() {
        int total = lastResponse.jsonPath().getInt("total");
        assertEquals(total, userIds.size(), "Total users count mismatch");
    }

    // ---------------- Single User ----------------
    @Given("I make a search for user {int}")
    public void search_for_user(int userId) {
        lastResponse = defaultRequest()
                .when()
                .get(ApiConstants.USERS + "/" + userId);
        System.out.println("Response Body: " + lastResponse.getBody().asString());
    }

    @Then("I should see the following user data")
    public void verify_single_user_data(DataTable table) {
        List<List<String>> rows = table.asLists();
        String expectedFirstName = rows.get(1).get(0);
        String expectedEmail = rows.get(1).get(1);

        assertEquals(lastResponse.statusCode(), 200);
        assertEquals(lastResponse.jsonPath().getString("data.first_name"), expectedFirstName);
        assertEquals(lastResponse.jsonPath().getString("data.email"), expectedEmail);
    }

    @Then("I receive error code {int} in response")
    public void verify_error_code(int statusCode) {
        assertEquals(lastResponse.statusCode(), statusCode);
    }

    // ---------------- Create User ----------------
    @Given("I create a user with name {string} and job {string}")
    public void create_user(String name, String job) {
        lastResponse = defaultRequest()
                .body(Map.of("name", name, "job", job))
                .when()
                .post(ApiConstants.USERS);

        if (!lastResponse.getContentType().contains("application/json")) {
            Assert.fail("Expected JSON response but got: " + lastResponse.getBody().asString());
        }
    }

    @Then("response should contain the following data")
    public void verify_created_user_data(DataTable dataTable) {
        List<String> expectedFields = dataTable.row(0);

        for (String field : expectedFields) {
            Object value = lastResponse.jsonPath().get(field);
            Assert.assertNotNull(value, "Missing field: " + field);
            System.out.println(field + ": " + value);
        }
    }

    // ---------------- Login ----------------
    @Given("I login successfully with the following data")
    	public void login_successful(DataTable credentials) {
    	    performLogin(credentials);
    	}

    @Given("I login unsuccessfully with the following data")
    public void login_unsuccessful(DataTable credentials) {
        performLogin(credentials);
    }

    @Then("I should get a response code of {int}")
    public void verify_response_code(int expectedCode) {
        int actualCode = lastResponse.getStatusCode();
        Assert.assertEquals(actualCode, expectedCode, 
            "Expected HTTP status code " + expectedCode + " but got " + actualCode);
    }

    @Then("I should see the following response message:")
    public void verify_error_message(DataTable expectedMessageTable) {
        String expected = expectedMessageTable.asList().get(0);
        // Remove "key": if user kept it in the feature file
        if (expected.contains(":")) {
            expected = expected.split(":", 2)[1].trim().replaceAll("^\"|\"$", "");
        }
        assertEquals(lastResponse.jsonPath().getString("error"), expected);
    }

    // ---------------- List Users with Delayed Response ----------------
    @Given("I wait for the user list to load")
    public void wait_for_user_list() {
        // Simple retry mechanism instead of fixed sleep
        int retries = 5;
        while (retries > 0) {
            lastResponse = defaultRequest().when().get(ApiConstants.USERS + "?page=1");
            if (lastResponse.statusCode() == 200) break;
            retries--;
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }

        if (lastResponse.statusCode() != 200) {
            throw new RuntimeException("Failed to load user list after retries");
        }

        System.out.println("Response Body: " + lastResponse.getBody().asString());
    }

    @Then("I should see that every user has a unique id")
    public void verify_unique_user_ids() {
        List<Integer> ids = lastResponse.jsonPath().getList("data.id");
        Set<Integer> uniqueIds = new HashSet<>(ids);
        assertEquals(ids.size(), uniqueIds.size(), "User IDs are not unique");
    }
}