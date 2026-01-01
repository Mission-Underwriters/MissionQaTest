package Pages;

import Base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/*
 * Purpose:
 * Represents the Cart page where selected products
 * are displayed before checkout.
 *
 * Extends BasePage to reuse WebDriver instance
 * Navigates to CheckoutPage on checkout action
 *
 */
public class CartPage extends BasePage {

	// Initializes cart page elements
	public CartPage() {
		super();
		PageFactory.initElements(driver, this);
	}

	/* ================= Page Elements ================= */

	// List of all items currently added to the cart
	@FindBy(className = "cart_item")
	private List<WebElement> cartItems;

	// Checkout button to proceed to checkout flow
	@FindBy(id = "checkout")
	private WebElement checkoutBtn;

	/* ================= Actions / Validations ================= */

	// Returns the number of items present in the cart
	public int getCartItemsCount() {
		return cartItems.size();
	}

	// Clicks checkout and navigates to the Checkout page
	public CheckoutPage clickCheckout() {
		checkoutBtn.click();
		return new CheckoutPage();
	}
}
