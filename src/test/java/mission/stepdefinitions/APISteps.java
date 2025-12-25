package mission.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import mission.utils.LoadProp;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class APISteps {

    private static ThreadLocal<Response> response = new ThreadLocal<>();
    private List<Integer> allUserIds = new ArrayList<>();
    private int totalUsers;
    
    private io.restassured.specification.RequestSpecification request() {
        return given()
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-api-key",LoadProp.getProperty("ApiKey"));
    }

    @Given("the ReqRes API is available")
    public void api_is_available() {
    	RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = LoadProp.getProperty("ApiUrl");
    }

    // LIST USERS
    @When("I call the list users API for page {int}")
    public void list_users_api_for_page(int page) {
        response.set(request().get("/users?page=" + page));
    }

    @When("I fetch all users from the API")
    public void fetch_all_users_from_api() {
        Response res = request().get("/users?page=1");
        response.set(res);

        Assert.assertEquals(res.getStatusCode(), 200);

        totalUsers = res.jsonPath().getInt("total");
        int totalPages = res.jsonPath().getInt("total_pages");

        for (int page = 1; page <= totalPages; page++) {
            Response pageResponse = request().get("/users?page=" + page);
            List<Integer> pageUserIds = pageResponse.jsonPath().getList("data.id");
            allUserIds.addAll(pageUserIds);
        }
    }

    @When("I wait for the user list to load")
    public void load_users_with_delay() {
        response.set(request().get("/users?delay=3"));
    }

    @Then("the total users count should match the number of returned users")
    public void total_users_count() {
        Assert.assertEquals(allUserIds.size(), totalUsers);
    }

    @Then("all user ids should be unique")
    public void unique_user_ids() {
        Set<Integer> uniqueIds = new HashSet<>(allUserIds);
        Assert.assertEquals(uniqueIds.size(), allUserIds.size());
    }

    // SINGLE USER
    @When("I fetch user with id {int}")
    public void fetch_user_with_id(int userId) {
        response.set(request().get("/users/" + userId));
    }

    @Then("I should see that every user has a unique id")
    public void unique_id_for_every_user() {
    	unique_user_ids();
    }
    
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        Assert.assertEquals(statusCode, response.get().getStatusCode());
    }

    @Then("the user first name should be {string}")
    public void user_first_name(String firstName) {
        Assert.assertEquals(response.get().jsonPath().getString("data.first_name"), firstName);
    }

    @Then("the user email should be {string}")
    public void user_email(String email) {
        Assert.assertEquals(response.get().jsonPath().getString("data.email"), email);
    }

    // LOGIN
    @When("I login with email {string} and password {string}")
    public void login_with_email_and_password(String email, String password) {
        response.set(
        		request()                
                .body("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                .post("/login")
        );
    }

    @Then("the login response should contain token")
    public void login_response() {
        Assert.assertNotNull(response.get().jsonPath().getString("token"));
    }

    @Then("the error message should be {string}")
    public void error_message(String errorMessage) {
        Assert.assertEquals(response.get().jsonPath().getString("error"), errorMessage);
    }

    // CREATE USER
    @When("I create a new user with name {string} and job {string}")
    public void create_user(String name, String job) {
        response.set(
        		request()                
                .body("{\"name\":\"" + name + "\",\"job\":\"" + job + "\"}")
                .post("/users")
        );
    }

    @Then("the response should contain user name {string}")
    public void user_name_in_response(String name) {
        Assert.assertEquals(response.get().jsonPath().getString("name"), name);
    }

    // UPDATE USER
    @When("I update user with id {int} using name {string} and job {string}")
    public void update_user(int userId, String name, String job) {
        response.set(
        		request()                
                .body("{\"name\":\"" + name + "\",\"job\":\"" + job + "\"}")
                .put("/users/" + userId)
        );
    }

    // DELETE USER
    @When("I delete user with id {int}")
    public void delete_user(int userId) {
        response.set(request().delete("/users/" + userId));
    }
}
