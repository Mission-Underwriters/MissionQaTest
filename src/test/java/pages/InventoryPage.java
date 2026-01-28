package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    WebDriver driver;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    By cartBadge = By.className("shopping_cart_badge");
    By cartIcon = By.className("shopping_cart_link");

    public By addToCartBtn(String itemName) {
        return By.xpath("//div[text()='" + itemName + "']/ancestor::div[@class='inventory_item']//button");
    }

    public void addItem(String itemName) {
        driver.findElement(addToCartBtn(itemName)).click();
    }



    public int getCartCount() {
        return Integer.parseInt(driver.findElement(cartBadge).getText());
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }
}

