package Pages;

import Base.BasePage;
import utils.WaitUtils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    public HomePage() {
        super();
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    public boolean isInventoryPageDisplayed() {
        return WaitUtils.waitForVisible(inventoryList).isDisplayed();
    }

    public CartPage openCart() {
        cartIcon.click();
        return new CartPage();
    }
}
