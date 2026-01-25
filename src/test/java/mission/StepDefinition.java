package mission;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.Map;

public class StepDefinition {

    @Given("^I am on the home page$")
    public void iAmOnTheHomePage() {
        HomePage.homePage();
    }

    @Given("^I get the default list of users for on 1st page$")
    public void iGetTheDefaultListofusers() {
        response = client.getUsersByPage(1);

    }

    @When("I get the list of all users within every page")
    public void iGetTheListOfAllUsers() {
        response = client.getUsersByPage(2);
    }

    @Then("I should see total users count equals the number of user ids")
    public void iShouldMatchTotalCount() {
        List<Integer> ids = response.jsonPath().getList("data.id");
        assertEquals(ids.size(), new HashSet<>(ids).size());

    }

    @Given("I make a search for user (.*)")
    public void iMakeASearchForUser(String sUserID) {
        response = client.getUserById(sUserID);

    }

    @Then("I should see the following user data")
    public void IShouldSeeFollowingUserData(DataTable dt) {
        Map<String, String> expected = dt.asMap(String.class, String.class);

        assertEquals(response.jsonPath().getString("data.first_name"), expected.get("first_name"));
        assertEquals(response.jsonPath().getString("data.last_name"), expected.get("last_name"));
    }

    @Then("I receive error code (.*) in response")
    public void iReceiveErrorCodeInResponse(int responseCode) {

    }

    @Given("I create a user with following (.*) (.*)")
    public void iCreateUserWithFollowing(String sUsername, String sJob) {
    }

    @Then("response should contain the following data")
    public void iReceiveErrorCodeInResponse(DataTable dt) {

    }

    @Given("I login unsuccessfully with the following data")
    public void iLoginSuccesfullyWithFollowingData(DataTable dt) {
        Map<String, String> data = dt.asMap(String.class, String.class);
        response = client.loginUnsuccessful(data.get("email"), data.get("password"));

    }

    @Given("^I wait for the user list to load$")
    public void iWaitForUserListToLoad() {

    }

    @Then("I should see that every user has a unique id")
    public void iShouldSeeThatEveryUserHasAUniqueID() {
        List<Integer> ids = response.jsonPath().getList("data.id");
        assertEquals(ids.size(), new HashSet<>(ids).size());



        // Please not that we need to check all values are unique in the list.
    }

    @Then("^I should get a response code of (\\d+)$")
    public void iShouldGetAResponseCodeOf(int responseCode) {
        assertEquals(response.getStatusCode(), responseCode);
    }

    @And("^I should see the following response message:$")
    public void iShouldSeeTheFollowingResponseMessage() {
    }
}
