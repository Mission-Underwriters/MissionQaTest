package mission.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import mission.base.BasePage;

// CartPage represents the shopping cart page of the website.
public class CartPage extends BasePage{
	public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_quantity")
    private List<WebElement> quantities;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;
    
    private final int WAIT_TIME = 10; 

    // Returns the number of items currently in the cart 
    public int getItemCount() {
        return cartItems.size();
    }

    // Verifies that each item's quantity is 1 
    public boolean verifyQuantityIsOne() {
        for (WebElement qty : quantities) {
            if (!qty.getText().equals("1")) {
                return false;
            }
        }
        return true;
    }

    //Removes particular item from the cart by name by checking cart size gets reduced by 1 every time
    public void removeItem(String itemName) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
        int initialCount = cartItems.size();
        boolean itemFound = false;

        for (WebElement cartItem : cartItems) {
            String name = cartItem
                    .findElement(By.className("inventory_item_name"))
                    .getText();

            if (name.equalsIgnoreCase(itemName)) {
                cartItem.findElement(By.tagName("button")).click();
                itemFound = true;

                wait.until(driver ->
                        driver.findElements(By.cssSelector(".cart_item")).size() == initialCount - 1
                );
                break;
            }
        }

        if (!itemFound) {
            throw new RuntimeException("Item not found in cart: " + itemName);
        }
    }

    // Clicks the checkout button and returns checkout page
    public CheckoutPage clickCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
        wait.until(driver -> checkoutButton.isDisplayed() && checkoutButton.isEnabled());
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}