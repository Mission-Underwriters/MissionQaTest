package Pages;

import Base.BasePage;
import utils.WaitUtils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 * Purpose:
 * This page represents the Home / Inventory screen after a successful login.
 * It provides basic validations and navigation actions from the home page.
 *
 * Dependencies:
 * Extends BasePage to reuse WebDriver handling
 * Uses WaitUtils for synchronization
 * Navigates to CartPage
 */
public class HomePage extends BasePage {

    // Constructor initializes page elements
    public HomePage() {
        super();
        PageFactory.initElements(driver, this);
    }

    /* ================= Page Elements ================= */

    // Inventory list container shown after login
    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    // Cart icon displayed on top-right of the page
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    /* ================= Page Validation ================= */

    // Verifies that inventory page is displayed
    public boolean isInventoryPageDisplayed() {
        return WaitUtils.waitForVisible(inventoryList).isDisplayed();
    }

    /* ================= Navigation ================= */

    // Opens the cart page
    public CartPage openCart() {
        cartIcon.click();
        return new CartPage();
    }
}
