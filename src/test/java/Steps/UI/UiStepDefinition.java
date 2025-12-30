package Steps.UI;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import Pages.*;
import utils.WaitUtils;

import java.util.Map;

import static org.testng.Assert.*;

public class UiStepDefinition {

    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage overviewPage;
    private CheckoutCompletePage completePage;

    /* ================= Login ================= */

    @Given("I am logged into SauceDemo with valid credentials")
    public void loginToSauceDemo() {
        loginPage = new LoginPage();
        productPage = loginPage.login("standard_user", "secret_sauce");

        assertTrue(
                productPage.isProductPageDisplayed(),
                "Login failed: Products page was not displayed"
        );
    }

    /* ================= Cart Operations ================= */

    @When("I add {string} to the cart")
    public void addProductToCart(String productName) {
        productPage.addProductToCart(productName);
    }

    @When("I remove {string} from the cart")
    public void removeProductFromCart(String productName) {
        productPage.removeProductFromCart(productName);
    }

    @Then("the cart badge count should be {string}")
    public void verifyCartBadgeCount(String expectedCount) {
        assertEquals(
                productPage.getCartBadgeCount(),
                expectedCount,
                "Cart badge count does not match"
        );
    }

    /* ================= Sorting ================= */

    @When("I sort products by {string}")
    public void sortProducts(String sortOption) {
        productPage.sortProducts(sortOption);
    }

    @Then("products should be sorted by price in ascending order")
    public void verifyProductsSortedLowToHigh() {
        assertTrue(
                productPage.isProductSortedByPriceLowToHigh(),
                "Products are not sorted by price (low to high)"
        );
    }

    /* ================= Checkout ================= */

    @When("I proceed to checkout")
    public void proceedToCheckout() {
        cartPage = productPage.goToCart();
        checkoutPage = cartPage.clickCheckout();
    }

    @And("I enter checkout details")
    public void enterCheckoutDetails(DataTable table) {

        Map<String, String> data =
                table.asMaps(String.class, String.class).get(0);

        // 🔥 IMPORTANT FIX: Capture next page
        overviewPage = checkoutPage.enterCheckoutDetails(
                data.get("firstName"),
                data.get("lastName"),
                data.get("postalCode")
        );
    }

    @When("I finish the checkout")
    public void finishCheckout() {
        completePage = overviewPage.finishCheckout();
    }

    @Then("I should see the order confirmation message {string}")
    public void verifyOrderConfirmation(String expectedMessage) {
        assertEquals(
                completePage.getConfirmationMessage(),
                expectedMessage,
                "Order confirmation message is incorrect"
        );
    }

    /* ================= Logout ================= */

    @When("I logout from the application")
    public void logoutFromApplication() {
        loginPage = productPage.logout();
    }

    @Then("I should be logged out successfully")
    public void verifyLogout() {
        assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Logout failed: Login page not displayed"
        );
    }
}
