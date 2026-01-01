package mission.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import mission.base.BasePage;

// CheckoutPage represents the checkout section of the website.
public class CheckoutPage extends BasePage {
	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "first-name")
	private WebElement firstName;

	@FindBy(id = "last-name")
	private WebElement lastName;

	@FindBy(id = "postal-code")
	private WebElement postalCode;

	@FindBy(id = "continue")
	private WebElement continueButton;

	@FindBy(css = ".inventory_item_price")
	private List<WebElement> prices;

	@FindBy(css = ".summary_subtotal_label")
	private WebElement itemTotal;

	@FindBy(css = ".summary_tax_label")
	private WebElement tax;

	@FindBy(css = ".summary_total_label")
	private WebElement total;

	@FindBy(id = "finish")
	private WebElement finishButton;

	@FindBy(className = "complete-header")
	private WebElement confirmationMessage;

	// Fill out the form with the buyer’s details.
	public void enterCheckoutInfo(String fname, String lname, String zip) {
		if (fname != null && !fname.isEmpty()) {
			firstName.sendKeys(fname);
		}
		if (lname != null && !lname.isEmpty()) {
			lastName.sendKeys(lname);
		}
		if (zip != null && !zip.isEmpty()) {
			postalCode.sendKeys(zip);
		}
	}

	// Clicks the continue button to proceed to the next checkout step.
	public void clickContinue() {
		continueButton.click();
	}

	// Add up the prices of everything in the cart.
	public double calculateItemTotal() {
		double total = 0;
		for (WebElement price : prices) {
			total += Double.parseDouble(price.getText().replace("$", ""));
		}
		return total;
	}

	// Gets the item total displayed on the page.
	public double getItemTotalFromPage() {
		return Double.parseDouble(itemTotal.getText().replace("Item total: $", ""));
	}

	// Gets the tax amount displayed on the page.
	public double getTaxFromPage() {
		return Double.parseDouble(tax.getText().replace("Tax: $", ""));
	}

	// Gets the total (item total + tax) displayed on the page.
	public double getTotalFromPage() {
		return Double.parseDouble(total.getText().replace("Total: $", ""));
	}

	// Click the finish button to complete the checkout process.
	public void clickFinish() {
		finishButton.click();
	}

	// Returns the confirmation message shown after successful checkout.
	public String getConfirmationMessage() {
		return confirmationMessage.getText();
	}

}