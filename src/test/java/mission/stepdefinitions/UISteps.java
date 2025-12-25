package mission.stepdefinitions;

import java.util.List;
import java.util.Map;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mission.pages.CartPage;
import mission.pages.CheckoutPage;
import mission.pages.InventoryPage;
import mission.pages.LoginPage;
import mission.utils.DriverFactory;
import mission.utils.LoadProp;

public class UISteps {

    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @Given("I open SauceDemo application")
    public void open_app() {
        DriverFactory.getDriver().get(LoadProp.getProperty("SaucedemoUrl"));
        loginPage = new LoginPage(DriverFactory.getDriver());
    }

    @When("I login with username {string} and password {string}")
    public void login(String user, String pass) {
    	if (loginPage.isUserLoggedIn()) {return;}
        loginPage.login(user, pass);
    }

    @Then("I should see {string}")
    public void verify_login(String result) {

        if (result.equalsIgnoreCase("login success")) {
            Assert.assertTrue(loginPage.isUserLoggedIn(),"User is not logged in");
        } 
        else if (result.equalsIgnoreCase("user locked error")) {
            Assert.assertTrue(loginPage.isErrorDisplayed(),"No error was shown");
        }
    }

    @When("I add the following items to the cart")
    public void add_items(List<String> items) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        items.forEach(inventoryPage::addItem);
        inventoryPage.openCart();
        cartPage = new CartPage(DriverFactory.getDriver());
    }

    @Then("I should see {int} items in the cart")
    public void verify_cart_count(int count) {
        Assert.assertEquals(cartPage.getItemCount(), count);
    }

    @When("I remove item {string}")
    public void remove_item(String item) {
        inventoryPage.removeItem(item);
        inventoryPage.openCart();
    }

    @When("I proceed to checkout")
    public void proceed_checkout() {
        cartPage.checkout();
        checkoutPage = new CheckoutPage(DriverFactory.getDriver());
    }

    @When("I enter checkout details")
    public void enter_details(List<Map<String,String>> data) {
        Map<String,String> row = data.get(0);
        checkoutPage.enterDetails(row.get("firstName"), row.get("lastName"), row.get("zipCode"));
    }

    @Then("item total should be correct")
    public void verify_total() {
        Assert.assertTrue(checkoutPage.getItemTotal() > 0);
    }

    @Then("tax should be calculated as {int} percent")
    public void verify_tax(int percent) {
        double itemTotal = checkoutPage.getItemTotal();
        double expectedTax = itemTotal * percent / 100;

        double actualTax = checkoutPage.getTax();

        Assert.assertEquals(
                actualTax,
                expectedTax,
                0.01,
                "Tax calculation is incorrect"
        );
    }
}