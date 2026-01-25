package saucedemo.ui.tests;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.ui.pageObjects.InventoryPage;
import saucedemo.ui.pageObjects.LoginPage;

/**
 * @author myname
 */
public class InventoryTest {
    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.LoginApplication("standard_user", "secret_sauce");
    }

    @Test
    public void verifyProductCanBeAddedToCart() {

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "1");
    }

    @Test
    public void verifyUserCanNavigateToCartFromInventory() {

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.goToCart();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"));
    }
}

