package saucedemo.ui.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.ui.pageObjects.CartPage;
import saucedemo.ui.pageObjects.InventoryPage;
import saucedemo.ui.pageObjects.LoginPage;

/**
 * @author myname
 */
public class CartTest extends BaseTest {

    @BeforeMethod
    public void loginAndAddProduct() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.LoginApplication("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
    }

    @Test
    public void verifyUserProceedToCheckoutFromCart() {

        CartPage cartPage = new CartPage(driver);
        cartPage.goToCheckout();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
    }
}

}
