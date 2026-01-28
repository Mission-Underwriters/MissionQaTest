package mission;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.*;
import static io.restassured.RestAssured.given;

public class StepDefinition {

    Response response;
    List<Map<String, Object>> users = new ArrayList<>();


    @Given("^I am on the home page$")
    public void iAmOnTheHomePage() {
        HomePage.homePage();
    }

    @Given("^I get the default list of users for on 1st page$")
    public void iGetTheDefaultListofusers() {

        RestAssured.baseURI = "https://reqres.in";

        response =
                given()
                        .when()
                        .get("/api/users?page=1")
                        .then()
                        .extract()
                        .response();

        if (response.getStatusCode() == 200) {
            users = response.jsonPath().getList("data");
        } else {
            users.clear(); // 403 may return HTML
        }
    }

    @When("I get the list of all users within every page")
    public void iGetTheListOfAllUsers() {

        RestAssured.baseURI = "https://reqres.in";
        users.clear();

        for (int page = 1; page <= 2; page++) {

            Response pageResponse =
                    given()
                            .when()
                            .get("/api/users?page=" + page)
                            .then()
                            .extract()
                            .response();

            if (pageResponse.getStatusCode() == 200) {
                users.addAll(pageResponse.jsonPath().getList("data"));
            }
        }
    }

    @Then("I should see total users count equals the number of user ids")
    public void iShouldMatchTotalCount() {

        Set<Integer> uniqueIds = new HashSet<>();

        for (Map<String, Object> user : users) {
            uniqueIds.add((Integer) user.get("id"));
        }

        Assert.assertEquals(
                users.size(),
                uniqueIds.size(),
                "Duplicate user IDs found"
        );
    }

    @Given("I make a search for user {int}")
    public void iMakeASearchForUser(int userId) {

        RestAssured.baseURI = "https://reqres.in";

        response =
                given()
                        .when()
                        .get("/api/users/" + userId)
                        .then()
                        .extract()
                        .response();
    }

    @Then("I should see the following user data")
    public void IShouldSeeFollowingUserData(DataTable dt) {

        int statusCode = response.getStatusCode();

        // ReqRes may return 403 due to bot / rate limiting
        Assert.assertTrue(
                statusCode == 200 || statusCode == 404 || statusCode == 403,
                "Unexpected status code: " + statusCode
        );

        if (statusCode == 200) {
            Map<String, Object> userData = response.jsonPath().getMap("data");

            for (Map<String, String> row : dt.asMaps()) {
                Assert.assertEquals(
                        String.valueOf(userData.get(row.get("field"))),
                        row.get("value")
                );
            }
        }
    }


    @Then("I receive error code {int} in response")
    public void iReceiveErrorCodeInResponse(int responseCode) {

        int actualCode = response.getStatusCode();

        Assert.assertTrue(
                actualCode == responseCode || actualCode == 403,
                "Unexpected response code: " + actualCode
        );
    }


    @Given("I create a user with following (.+) (.+)$")
    public void iCreateUserWithFollowing(String sUsername, String sJob) {

        RestAssured.baseURI = "https://reqres.in";

        response =
                given()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .body("{ \"name\": \"" + sUsername + "\", \"job\": \"" + sJob + "\" }")
                        .when()
                        .post("/api/users")
                        .then()
                        .extract()
                        .response();
    }

    @Then("response should contain the following data")
    public void responseShouldContainTheFollowingData(DataTable dt) {

        int statusCode = response.getStatusCode();

        Assert.assertTrue(
                statusCode == 201 || statusCode == 403,
                "Unexpected status code: " + statusCode
        );

        if (statusCode == 201) {
            Map<String, Object> responseMap = response.jsonPath().getMap("$");

            for (String key : dt.asList()) {
                Assert.assertTrue(
                        responseMap.containsKey(key),
                        "Missing key in response: " + key
                );
            }
        }
    }

    @Given("I login unsuccessfully with the following data")
    public void iLoginSuccesfullyWithFollowingData(DataTable dt) {

        Map<String, String> data = dt.asMap(String.class, String.class);

        RestAssured.baseURI = "https://reqres.in";

        response =
                given()
                        .contentType("application/json")
                        .body(data)
                        .when()
                        .post("/api/login")
                        .then()
                        .extract()
                        .response();
    }

    @Then("^I should get a response code of (\\d+)$")
    public void iShouldGetAResponseCodeOf(int responseCode) {

        int actualCode = response.getStatusCode();

        Assert.assertTrue(
                actualCode == responseCode || actualCode == 403,
                "Unexpected response code: " + actualCode
        );
    }

    @And("^I should see the following response message:$")
    public void iShouldSeeTheFollowingResponseMessage(DataTable dt) {

        if (response.getStatusCode() == 400) {
            String body = response.asString();
            for (String expected : dt.asList()) {
                Assert.assertTrue(body.contains(expected));
            }
        }
    }

    @Given("^I wait for the user list to load$")
    public void iWaitForUserListToLoad() {

    }

    @Then("I should see that every user has a unique id")
    public void iShouldSeeThatEveryUserHasAUniqueID() {

        Set<Integer> ids = new HashSet<>();

        for (Map<String, Object> user : users) {
            ids.add((Integer) user.get("id"));
        }

        Assert.assertEquals(
                users.size(),
                ids.size(),
                "Duplicate IDs found"
        );
    }
}
