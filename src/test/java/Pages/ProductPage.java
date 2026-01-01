package Pages;

import Base.BasePage;
import utils.WaitUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Purpose:
 * This page represents the Products (Inventory) screen of SauceDemo.
 * It handles product listing actions such as add/remove to cart,
 * sorting products and logout operations.
 *
 * Dependencies:
 * Extends BasePage to reuse WebDriver initialization
 * Uses WaitUtils for stable waits, Navigates to CartPage and LoginPage
 */

public class ProductPage extends BasePage {

    public ProductPage() {
        super();
        PageFactory.initElements(driver, this);
        WaitUtils.waitForPageLoad();
    }

    /* ================= Locators ================= */

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuBtn;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    /* ================= Validation ================= */

    public boolean isProductPageDisplayed() {
        return WaitUtils.waitForVisible(inventoryList).isDisplayed();
    }

    /* ================= Cart ================= */

    public void addProductToCart(String productName) {
        By addBtn = By.xpath(
                "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button"
        );
        WaitUtils.waitForClickable(addBtn).click();
    }

    public void removeProductFromCart(String productName) {
        By removeBtn = By.xpath(
                "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button"
        );
        WaitUtils.waitForClickable(removeBtn).click();
    }

    public String getCartBadgeCount() {
        try {
            return cartBadge.getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public CartPage goToCart() {
        WaitUtils.waitForClickable(cartIcon).click();
        return new CartPage();
    }

    /* ================= Sorting ================= */

    public void sortProducts(String option) {
        WaitUtils.waitForClickable(sortDropdown).click();
        By sortOption = By.xpath("//option[text()='" + option + "']");
        WaitUtils.waitForClickable(sortOption).click();
    }

    public boolean isProductSortedByPriceLowToHigh() {
        List<Double> actualPrices = new ArrayList<>();

        for (WebElement price : productPrices) {
            actualPrices.add(
                    Double.parseDouble(price.getText().replace("$", ""))
            );
        }

        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);

        return actualPrices.equals(sortedPrices);
    }

    /* ================= Logout ================= */

    public LoginPage logout() {
        WaitUtils.waitForClickable(menuBtn).click();
        WaitUtils.waitForVisible(By.className("bm-menu"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", logoutLink);

        WaitUtils.waitForPageLoad();
        return new LoginPage();
    }
}
