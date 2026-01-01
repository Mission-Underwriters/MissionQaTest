package mission.stepDefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mission.api.RestClient;
import mission.api.RetryUtils;
import mission.api.UserService;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// This class contains step definitions for user-related API scenarios(i.e: Fetching users, Searching for a single user, Creating, updating, and deleting users)
public class API_UserStepDefs extends API_CommonStepDefs {

	private final UserService userService = new UserService(new RestClient());

	private final Set<Integer> collectedUserIds = new HashSet<>();

	@Given("I get the default list of users for on 1st page")
	public void getFirstPageUsers() {
		setResponse(userService.getUsersByPage(1));
		verifyStatusCode(200);
	}

	@When("I get the list of all users within every page")
	public void getAllUsersAcrossPages() {
		int totalPages = getResponse().jsonPath().getInt("total_pages");
		for (int page = 1; page <= totalPages; page++) {
			List<Integer> ids = userService.getUsersByPage(page).jsonPath().getList("data.id");
			collectedUserIds.addAll(ids);
		}
	}

	@Then("I should see total users count equals the number of user ids")
	public void verifyTotalUserCount() {
		int total = getResponse().jsonPath().getInt("total");
		Assert.assertEquals(collectedUserIds.size(), total);
	}

	@Given("I make a search for user {int}")
	public void searchForUser(int userId) {
		setResponse(userService.getSingleUser(userId));
	}

	@Then("I should see the following user data")
	public void verifySingleUserData(DataTable table) {
		List<List<String>> rows = table.asLists();
		assertJsonResponse();
		Assert.assertEquals(getResponse().jsonPath().getString("data.first_name"), rows.get(1).get(0));
		Assert.assertEquals(getResponse().jsonPath().getString("data.email"), rows.get(1).get(1));
	}

	@Then("I receive error code {int} in response")
	public void verifyErrorCode(int statusCode) {
		verifyStatusCode(statusCode);
	}

	@Given("I create a user with name {string} and job {string}")
	public void createUser(String name, String job) {
		setResponse(userService.createUser(name, job));
	}

	@Then("response should contain the following data")
	public void verifyCreatedUserFields(DataTable table) {
		for (String field : table.row(0)) {
			Assert.assertNotNull(getResponse().jsonPath().get(field), "Missing field: " + field);
		}
	}

	@Given("I wait for the user list to load")
	public void waitForUserList() {
		setResponse(RetryUtils.retry(() -> userService.getUsersByPage(1), 5, 1000));
	}

	@Then("I should see that every user has a unique id")
	public void verifyUniqueUserIds() {
		List<Integer> ids = getResponse().jsonPath().getList("data.id");
		Assert.assertEquals(ids.size(), new HashSet<>(ids).size());
	}

	@Given("I update user {int} with the following data")
	public void updateUser(int userId, DataTable table) {
	    List<Map<String, String>> rows = table.asMaps(String.class, String.class);
	    Map<String, String> data = rows.get(0); // first row
	    setResponse(userService.updateUser(userId, data));
	    verifyStatusCode(200);
	}

	@Then("response should contain the updated data")
	public void verifyUpdatedUserData(DataTable table) {
	    List<String> headers = table.row(0);
	    for (String field : headers) {
	        if (field.equalsIgnoreCase("updatedAt")) {
	            Assert.assertNotNull(getResponse().jsonPath().getString(field), field + " is missing in response");
	        } else {
	            Assert.assertNotNull(getResponse().jsonPath().getString(field),
	                    field + " is missing or null in response");
	        }
	    }
	}
	
	@Given("I delete user {int}")
	public void deleteUser(int userId) {
	    setResponse(userService.deleteUser(userId));
	    verifyStatusCode(204); 
	}
}