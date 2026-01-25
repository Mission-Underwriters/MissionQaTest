package saucedemo.ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.ui.pageObjects.CartPage;
import saucedemo.ui.pageObjects.CheckoutPage;
import saucedemo.ui.pageObjects.InventoryPage;
import saucedemo.ui.pageObjects.LoginPage;

import static mission.BasePage.driver;

/**
 * @author myname
 */
public class CheckoutTest {

    @BeforeMethod
    public void reachCheckoutPage() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.LoginApplication("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.goToCheckout();
    }

    @Test
    public void verifyCheckoutInformationIsAccepted() {

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterDetails("Markandey", "Pandey", "122011");

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
    }
}
