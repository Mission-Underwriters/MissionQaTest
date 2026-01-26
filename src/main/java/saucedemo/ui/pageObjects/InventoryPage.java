package saucedemo.ui.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // All product containers
    @FindBy(className = "inventory_item")
    private List<WebElement> products;

    // Cart icon
    @FindBy(className = "shopping_cart_link")
    private WebElement cartButton;

    // Wait until products are visible
    public List<WebElement> getProductList() {
        wait.until(ExpectedConditions.visibilityOfAllElements(products));
        return products;
    }

    // Add product to cart by name
    public void addProductToCart(String productName) {
        for (WebElement product : getProductList()) {
            String name = product.findElement(By.className("inventory_item_name")).getText();
            if (name.equalsIgnoreCase(productName)) {
                product.findElement(By.tagName("button")).click();
                break;
            }
        }
    }

    public void goToCart() {
        cartButton.click();
    }
}
