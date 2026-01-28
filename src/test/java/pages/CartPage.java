package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage {

    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    By checkoutBtn = By.id("checkout");

    public List<Integer> getAllItemQuantities() {
        List<Integer> quantities = new ArrayList<>();
        List<WebElement> qtyElements = driver.findElements(By.className("cart_quantity"));

        for (WebElement qty : qtyElements) {
            quantities.add(Integer.parseInt(qty.getText()));
        }
        return quantities;
    }


    public void clickCheckout() {
        driver.findElement(checkoutBtn).click();
    }
    public void removeItem(String itemName) {
        String xpath =
                "//div[@class='cart_item']//div[text()='" + itemName + "']" +
                        "/ancestor::div[@class='cart_item']//button[text()='Remove']";

        driver.findElement(By.xpath(xpath)).click();
    }

}

