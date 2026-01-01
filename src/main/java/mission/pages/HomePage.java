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

// HomePage represents the main shopping page of the website.
public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = ".inventory_item_name")
	private List<WebElement> items;

	@FindBy(css = ".btn_inventory")
	private List<WebElement> addToCartButtons;

	@FindBy(className = "shopping_cart_link")
	private WebElement cartLink;

	@FindBy(css = ".shopping_cart_badge")
	private WebElement cartBadge;

	// Pick the items from the page and add them to the shopping cart.
	public void addItemsToCart(List<String> items) {
		for (String item : items) {
			WebElement addButton = driver.findElement(
					By.xpath("//div[text()='" + item + "']/ancestor::div[@class='inventory_item']//button"));
			addButton.click();
		}
	}

	// Check the number showing on the cart icon.
	public int getCartCount() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(cartBadge));
			return Integer.parseInt(cartBadge.getText());
		} catch (TimeoutException e) {
			return 0;
		}
	}

	// Go to the shopping cart page to see what’s inside it.
	public CartPage goToCart() {
		cartLink.click();
		return new CartPage(driver);
	}
}