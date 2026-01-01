package Pages;

import Base.BasePage;
import utils.WaitUtils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 * Purpose:
 * Represents the final confirmation page shown
 * after a successful checkout.
 *
 * Extends BasePage to reuse WebDriver setup
 * Uses WaitUtils for reliable element access
 *
 */
public class CheckoutCompletePage extends BasePage {

	// Constructor initializes confirmation page elements
	public CheckoutCompletePage() {
		super();
		PageFactory.initElements(driver, this);
	}

	/* ================= Page Elements ================= */

	@FindBy(className = "complete-header")
	private WebElement confirmationMsg;

	/* ================= Validation ================= */

	// Returns the success message displayed after order completion
	public String getConfirmationMessage() {
		return WaitUtils.waitForVisible(confirmationMsg).getText();
	}
}
