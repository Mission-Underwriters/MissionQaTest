package Steps.UI;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import Pages.*;

import java.util.Map;

import static org.testng.Assert.*;

/*
 * Purpose:
 * This class contains UI step definitions for SauceDemo application.
 * It connects Gherkin steps from feature files with Page Object actions.
 *
 * Feature File  -> Step Definition  -> Page Classes
 */
public class UiStepDefinition {

    // Page object references used across scenarios
    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage overviewPage;
    private CheckoutCompletePage completePage;

    /* =========================================================
       BACKGROUND LOGIN
       Used by scenarios where user must be logged in already
       ========================================================= */

    @Given("I am logged into SauceDemo with valid credentials")
    public void loginToSauceDemo() {

        // Login using default valid credentials
        loginPage = new LoginPage();
        productPage = loginPage.login("standard_user", "secret_sauce");

        // Basic validation to ensure login success
        assertNotNull(productPage, "Product page is null after login");
        assertTrue(
                productPage.isProductPageDisplayed(),
                "Login failed: Products page was not displayed"
        );
    }

    /* =========================================================
       LOGIN PAGE NAVIGATION
       Used only for login-related scenarios
       ========================================================= */

    @Given("I am on SauceDemo login page")
    public void navigateToLoginPage() {

        // Opening login page directly
        loginPage = new LoginPage();

        assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Login page is not displayed"
        );
    }

    /* =========================================================
       LOGIN ACTIONS
       ========================================================= */

    @When("I login with username {string} and password {string}")
    public void loginWithUsernameAndPassword(String username, String password) {

        // Perform login action; result may stay on login page or go to product page
        productPage = loginPage.login(username, password);
    }

    @Then("I should see login error message {string}")
    public void verifyLoginErrorMessage(String expectedMessage) {

        // After failed login, user should remain on login page
        assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Expected to stay on login page after failed login"
        );

        assertTrue(
                loginPage.getLoginErrorMessage().contains(expectedMessage),
                "Login error message does not match"
        );
    }

    @Then("I should be logged in successfully")
    public void verifySuccessfulLogin() {

        // Successful login should land on product page
        assertNotNull(productPage, "Product page is null");
        assertTrue(
                productPage.isProductPageDisplayed(),
                "Login failed: Product page not displayed"
        );
    }

    /* =========================================================
       CART OPERATIONS
       ========================================================= */

    @When("I add {string} to the cart")
    public void addProductToCart(String productName) {

        // Add specific product to cart by name
        productPage.addProductToCart(productName);
    }

    @When("I remove {string} from the cart")
    public void removeProductFromCart(String productName) {

        // Remove specific product from cart
        productPage.removeProductFromCart(productName);
    }

    @Then("the cart badge count should be {string}")
    public void verifyCartBadgeCount(String expectedCount) {

        // Validate cart icon badge count
        assertEquals(
                productPage.getCartBadgeCount(),
                expectedCount,
                "Cart badge count does not match"
        );
    }

    /* =========================================================
       SORTING
       ========================================================= */

    @When("I sort products by {string}")
    public void sortProducts(String sortOption) {

        // Apply sorting option from dropdown
        productPage.sortProducts(sortOption);
    }

    @Then("products should be sorted by price in ascending order")
    public void verifyProductsSortedLowToHigh() {

        // Validate product prices are sorted correctly
        assertTrue(
                productPage.isProductSortedByPriceLowToHigh(),
                "Products are not sorted by price (low to high)"
        );
    }

    /* =========================================================
       CHECKOUT
       ========================================================= */

    @When("I proceed to checkout")
    public void proceedToCheckout() {

        // Navigate from product page to cart and then to checkout
        cartPage = productPage.goToCart();
        checkoutPage = cartPage.clickCheckout();
    }

    @And("I enter checkout details")
    public void enterCheckoutDetails(DataTable table) {

        // Read checkout details from feature file
        Map<String, String> data =
                table.asMaps(String.class, String.class).get(0);

        overviewPage = checkoutPage.enterCheckoutDetails(
                data.get("firstName"),
                data.get("lastName"),
                data.get("postalCode")
        );
    }

    @And("I finish the checkout")
    public void finishCheckout() {

        // Complete the checkout process
        completePage = overviewPage.finishCheckout();
    }

    @Then("I should see the order confirmation message {string}")
    public void verifyOrderConfirmation(String expectedMessage) {

        // Validate final confirmation message
        assertEquals(
                completePage.getConfirmationMessage(),
                expectedMessage,
                "Order confirmation message is incorrect"
        );
    }

    /* =========================================================
       LOGOUT
       ========================================================= */

    @When("I logout from the application")
    public void logoutFromApplication() {

        // Logout from product page
        loginPage = productPage.logout();
    }

    @Then("I should be logged out successfully")
    public void verifyLogout() {

        // After logout, login page should be visible
        assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Logout failed: Login page not displayed"
        );
    }
}
