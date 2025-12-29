package mission.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import mission.base.BasePage;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
    }

        @FindBy(css = ".inventory_item_name")
        private List<WebElement> items;

        @FindBy(css = ".btn_inventory")
        private List<WebElement> addToCartButtons;

        @FindBy(className = "shopping_cart_link")
        private WebElement cartLink;
        
        @FindBy(css = ".shopping_cart_badge")
        private WebElement cartBadge;

        public void addItemsToCart(List<String> items) {
            for (String item : items) {
                WebElement addButton = driver.findElement(
                    By.xpath("//div[text()='" + item + "']/ancestor::div[@class='inventory_item']//button")
                );
                addButton.click();

                // Wait for button text to change to REMOVE
               // new WebDriverWait(driver, Duration.ofSeconds(5))
                 //   .until(ExpectedConditions.textToBePresentInElement(addButton, "Remove"));
            }
        }


        public int getCartCount() {
           // String count = cartLink.getText();
            //return count.isEmpty() ? 0 : Integer.parseInt(count);
        	try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(cartBadge));
                return Integer.parseInt(cartBadge.getText());
            } catch (TimeoutException e) {
                return 0; // No badge means empty cart
            }
        
        }

        public void goToCart() {
            cartLink.click();
        }
}