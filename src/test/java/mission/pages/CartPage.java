package mission.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class CartPage {

    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getItemCount() {
        List<?> items = driver.findElements(By.className("cart_item"));
        return items.size();
    }

    public void checkout() {
        driver.findElement(By.id("checkout")).click();
    }
}