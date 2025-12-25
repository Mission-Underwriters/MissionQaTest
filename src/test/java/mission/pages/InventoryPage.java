package mission.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import mission.utils.DriverFactory;

public class InventoryPage {

    private WebDriver driver;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addItem(String itemName) {
        driver.findElement(By.xpath("//div[text()='" + itemName + "']/ancestor::div[@class='inventory_item']//button")).click();
    }

    public void removeItem(String itemName) {
        String xpath = "//div[@class='inventory_item_name' and text()='" + itemName + "']" +"/ancestor::div[@class='cart_item_label']//button";
        DriverFactory.getDriver().findElement(By.xpath(xpath)).click();
    }

    public void openCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }
}