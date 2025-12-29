package mission.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import mission.base.BasePage;

public class CheckoutPage extends BasePage{
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

    public void clickContinue() {
        continueButton.click();
    }

    public double calculateItemTotal() {
        double total = 0;
        for (WebElement price : prices) {
            total += Double.parseDouble(price.getText().replace("$", ""));
        }
        return total;
    }

    public double getItemTotalFromPage() {
        return Double.parseDouble(
                itemTotal.getText().replace("Item total: $", "")
        );
    }

    public double getTaxFromPage() {
        return Double.parseDouble(
                tax.getText().replace("Tax: $", "")
        );
    }
    
    public double getTotalFromPage() {
        return Double.parseDouble(
                total.getText().replace("Total: $", "")
        );
    }

    public void clickFinish() {
        finishButton.click();
    }

    public String getConfirmationMessage() {
        return confirmationMessage.getText();
    }

    public boolean isCheckoutComplete() {
        return driver.getCurrentUrl().contains("checkout-complete");
    }
}