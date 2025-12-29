package mission.stepDefs;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import mission.hook.Hook;
import mission.pages.LoginPage;

public class UIStepDefinition {

	private WebDriver driver;

    private LoginPage loginPage;
  

    // 🔹 Initialize driver & pages AFTER @Before hook
    private void initPages() {
        if (driver == null) {
            driver = Hook.driver;
            loginPage = new LoginPage(driver);
        }
    }

    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
        initPages();
        assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
    }

    @And("I login in with the following details")
    public void i_login_with_details(io.cucumber.datatable.DataTable dataTable) {
        initPages();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            loginPage.login(row.get("userName"), row.get("Password"));
        }
    }
}
