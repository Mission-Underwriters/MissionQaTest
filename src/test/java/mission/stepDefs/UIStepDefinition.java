package mission.stepDefs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mission.hook.Hook;
import mission.pages.*;

public class UIStepDefinition {

	private WebDriver driver;

    private LoginPage loginPage;
    private HomePage homePage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    
    private List<String> itemsAdded = new ArrayList<>();
  
    //Initialize driver 
    private void initPages() {
        if (driver == null) {
            driver = Hook.driver;
            loginPage = new LoginPage(driver);
            homePage = new HomePage(driver);
            cartPage = new CartPage(driver);
            checkoutPage = new CheckoutPage(driver);
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
    
    @And("I add the following items to the basket")
    public void i_add_items_to_basket(io.cucumber.datatable.DataTable dataTable) {
        initPages();
        List<String> items = new ArrayList<>();
        for (List<String> row : dataTable.asLists(String.class)) {
            items.add(row.get(0));
        }
        homePage.addItemsToCart(items);
        itemsAdded.addAll(items);
    }

    @And("I should see {int} items added to the shopping cart")
    public void i_should_see_items_in_cart(int expectedCount) {
        initPages();
        assertEquals(homePage.getCartCount(), expectedCount);
    }

    @And("I click on the shopping cart")
    public void i_click_on_shopping_cart() {
        initPages();
        homePage.goToCart();
    }

    @And("I verify that the QTY count for each item should be 1")
    public void verify_quantity_for_each_item() {
        initPages();
        assertTrue(cartPage.verifyQuantityIsOne(), "Not all items have quantity 1");
    }

    @And("I remove the following item:")
    public void remove_item(io.cucumber.datatable.DataTable dataTable) {
        initPages();
        String itemToRemove = dataTable.asLists(String.class).get(0).get(0);
        cartPage.removeItem(itemToRemove);
        itemsAdded.remove(itemToRemove);
    }

    @And("I click on the CHECKOUT button")
    public void click_checkout() {
        initPages();
        cartPage.clickCheckout();
    }

    @And("I type {string} for First Name")
    public void type_first_name(String firstName) {
        initPages();
        checkoutPage.enterCheckoutInfo(firstName, "", "");
    }

    @And("I type {string} for Last Name")
    public void type_last_name(String lastName) {
        initPages();
        checkoutPage.enterCheckoutInfo("", lastName, "");
    }

    @And("I type {string} for ZIP\\/Postal Code")
    public void type_zip(String zip) {
        initPages();
        checkoutPage.enterCheckoutInfo("", "", zip);
    }

    @When("I click on the CONTINUE button")
    public void click_continue() {
        initPages();
        checkoutPage.clickContinue();
    }

    @Then("Item total will be equal to the total of items on the list")
    public void verify_item_total() {
        initPages();
        double calculatedTotal = checkoutPage.calculateItemTotal();
        double displayedTotal = checkoutPage.getItemTotalFromPage();
        assertEquals(displayedTotal, calculatedTotal, 0.01);
    }

    @Then("a Tax rate of 8 % is applied to the total")
    public void verify_tax_rate() {
        initPages();
        double calculatedTotal = checkoutPage.calculateItemTotal();
        double expectedTax = Math.round(calculatedTotal * 0.08 * 100.0) / 100.0;
        double displayedTax = checkoutPage.getTaxFromPage();
        assertEquals(displayedTax, expectedTax, 0.01);
    }
    
    @And("the total amount should be equal to item total plus tax")
    public void verify_total_amount() {
        initPages();

        double itemTotal = checkoutPage.getItemTotalFromPage();
        double tax = checkoutPage.getTaxFromPage();
        double total = checkoutPage.getTotalFromPage();

        assertEquals(total, itemTotal + tax, 0.01,
                "Total amount is not equal to item total + tax");
    }

    @And("I click on the FINISH button")
    public void click_finish_button() {
        initPages();
        checkoutPage.clickFinish();
    }

    @Then("I should see the order confirmation message")
    public void verify_order_confirmation_message() {
        initPages();
        assertTrue(
                checkoutPage.getConfirmationMessage().contains("Thank you for your order!"),
                "Order confirmation message not displayed"
        );
    }

    @And("the checkout should be completed successfully")
    public void verify_checkout_completed() {
        initPages();
        assertTrue(
                checkoutPage.isCheckoutComplete(),
                "Checkout is not completed successfully"
        );
    }

    @And("the shopping cart should be empty")
    public void verify_cart_is_empty() {
        initPages();
        assertEquals(cartPage.getItemCount(), 0,
                "Shopping cart is not empty after checkout");
    }
}