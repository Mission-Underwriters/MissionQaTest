package saucedemo.ui.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InventoryPage {
    WebDriver driver;

    InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "inventory_list")
    List<WebElement> Products;

    @FindBy(id = "add-to-cart-sauce-labs-bolt-t-shirt")
    WebElement Cartbutton;

    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return products;
    }

    public void addProductToCart(String productName) {
        for (WebElement product : products) {
            String name = product.findElement(By.className("inventory_list")).getText();
            if (name.equalsIgnoreCase(productName)) {
                product.findElement(By.tagName("button")).click();
                break;
            }


        }

    }

    public void goToCart() {
        Cartbutton.click();

    }

}
