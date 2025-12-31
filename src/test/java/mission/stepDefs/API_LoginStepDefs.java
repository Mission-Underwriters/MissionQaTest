package mission.stepDefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import mission.api.LoginService;
import mission.api.RestClient;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class API_LoginStepDefs extends API_CommonStepDefs {

	private final LoginService loginService = new LoginService(new RestClient());

	@Given("I login successfully with the following data")
	public void loginSuccessfully(DataTable table) {
		performLogin(table);
	}

	@Given("I login unsuccessfully with the following data")
	public void loginUnsuccessfully(DataTable table) {
		performLogin(table);
	}

	@Then("I should get a response code of {int}")
	public void verifyLoginResponseCode(int statusCode) {
		verifyStatusCode(statusCode);
	}

	@Then("I should see the following response message:")
	public void verifyErrorMessage(DataTable table) {
		String expected = table.asList().get(0).replace("\"", "").split(":")[1].trim();

		String actual = getResponse().jsonPath().getString("error");

		Assert.assertEquals(actual, expected);
	}
	
	private void performLogin(DataTable table) {
	    // Get table rows
	    List<Map<String, String>> rows = table.asMaps(String.class, String.class);
	    Map<String, String> data = rows.get(0); // Only one row in login scenario

	    String email = data.get("Email"); // or normalize to "email"
	    String password = data.get("Password");

	    if (password != null && password.equalsIgnoreCase("[empty]")) {
	        password = "";
	    }

	    lastResponse = loginService.login(email, password);
	    //System.out.println("Login payload: " + lastResponse.asString());
	}

}
