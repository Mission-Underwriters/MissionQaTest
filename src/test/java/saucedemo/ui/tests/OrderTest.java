package saucedemo.ui.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.ui.pageObjects.*;

/**
 * @author myname
 */
public class OrderTest extends BaseTest {
    @Test
    public void verifyOrderIsPlacedSuccessfully() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.LoginApplication("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.goToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterDetails("Mark", "Pandey", "560001");

        OrderPage orderPage = new OrderPage(driver);
        orderPage.placeOrder();

        Assert.assertEquals(
                orderPage.getSuccessMessage(),
                "Thank you for your order!"
        );
    }
}
