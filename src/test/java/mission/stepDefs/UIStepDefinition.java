package mission.stepDefs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import mission.hook.Hook;

// UIStepDefinition contains all the step definitions for the Cucumber tests.
public class UIStepDefinition {

	// Validation for different users
	@Given("I login with username {string} and password {string}")
	 public void i_login_with_username_and_password(String userName, String password) {
        Hook.loginPage.login(userName, password);
    }
	
	@Then("I should see {string}")
	public void verify_login(String status) {

	    if (status.equalsIgnoreCase("Success")) {
	        Assert.assertTrue(
	                Hook.loginPage.isUserLoggedIn(),
	                "Expected login to succeed, but user is not logged in"
	        );
	    }
	    else if (status.equalsIgnoreCase("Fail")) {
	        Assert.assertTrue(
	                Hook.loginPage.isErrorDisplayed(),
	                "Expected login to fail, but error message was not displayed"
	        );
	    }
	}

	
	
	// LOGIN STEPS
	@Given("I am on the home page")
	public void i_am_on_the_home_page() {
		assertEquals(Hook.driver.getCurrentUrl(), "https://www.saucedemo.com/");
	}

	@And("I login with the user details")
	public void i_login_with_details(DataTable dataTable) {
		for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
			Hook.homePage = Hook.loginPage.login(row.get("userName"), row.get("Password"));
		}
	}

	// CART STEPS
	@When("I add the following items to the basket")
	@Given("I have added items to the basket")
	public void i_add_items_to_the_basket(DataTable dataTable) {
		List<String> items = dataTable.asList();
		Hook.homePage.addItemsToCart(items);
	}

	@Then("I should see {int} items added to the shopping cart")
	public void i_should_see_items_added_to_the_shopping_cart(int expectedCount) {
		assertEquals(Hook.homePage.getCartCount(), expectedCount, "Cart count mismatch");
	}

	@And("I open the shopping cart")
	public void i_open_the_shopping_cart() {
		Hook.cartPage = Hook.homePage.goToCart();
	}

	@When("I remove the following item:")
	public void i_remove_the_following_item(DataTable dataTable) {
		String itemToRemove = dataTable.asList().get(0);
		Hook.cartPage.removeItem(itemToRemove);
	}

	@Then("quantity for each item should be 1")
	public void quantity_for_each_item_should_be_one() {
		assertTrue(Hook.cartPage.verifyQuantityIsOne(), "Quantity is not 1 for all items");
	}

	// CHECKOUT STEPS

	@And("I proceed to checkout")
	public void i_proceed_to_checkout() {
		Hook.checkoutPage = Hook.cartPage.clickCheckout();
	}

	@And("I enter checkout information with First Name {string}, Last Name {string}, and ZIP {string}")
	public void i_enter_checkout_information(String firstName, String lastName, String zip) {
		Hook.checkoutPage.enterCheckoutInfo(firstName, lastName, zip);
	}

	@When("I continue checkout")
	public void i_continue_checkout() {
		Hook.checkoutPage.clickContinue();
	}

	// PRICE & TAX VALIDATIONS
	@Then("item total should match sum of item prices")
	public void item_total_should_match_sum_of_item_prices() {
		assertEquals(Hook.checkoutPage.getItemTotalFromPage(), Hook.checkoutPage.calculateItemTotal(), 0.01,
				"Item total does not match calculated total");
	}

	@Then("tax should be calculated at {int} percent")
	public void tax_should_be_calculated_at_percent(int taxRate) {
		double expectedTax = Math.round(Hook.checkoutPage.calculateItemTotal() * taxRate / 100 * 100.0) / 100.0;
		assertEquals(Hook.checkoutPage.getTaxFromPage(), expectedTax, 0.01, "Tax amount is incorrect");
	}

	@Then("total amount should be item total plus tax")
	public void total_amount_should_be_item_total_plus_tax() {
		double expectedTotal = Hook.checkoutPage.getItemTotalFromPage() + Hook.checkoutPage.getTaxFromPage();
		assertEquals(Hook.checkoutPage.getTotalFromPage(), expectedTotal, 0.01, "Total amount is incorrect");
	}

	// ORDER COMPLETION

	@When("I finish checkout")
	public void i_finish_checkout() {
		Hook.checkoutPage.clickFinish();
	}

	@Then("I should see the order confirmation message")
	public void i_should_see_the_order_confirmation_message() {
		assertTrue(Hook.checkoutPage.getConfirmationMessage().contains("Thank you for your order!"),
				"Order confirmation message not displayed");
	}
}