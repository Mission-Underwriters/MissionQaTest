package Pages;

import Base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

/*
 * Purpose:
 * Represents the Checkout Information page where user
 * enters personal and delivery details.
 *
 * Dependencies:
 * Extends BasePage for shared WebDriver instance
 * Uses WaitUtils for element synchronization
 * Navigates to CheckoutOverviewPage after form submission
 */
public class CheckoutPage extends BasePage {

    /* ================= Page Elements ================= */

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "postal-code")
    private WebElement postalCode;

    @FindBy(id = "continue")
    private WebElement continueBtn;

    // Constructor initializes checkout page elements
    public CheckoutPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    /* ================= Actions ================= */

    // Enters checkout details and navigates to overview page
    public CheckoutOverviewPage enterCheckoutDetails(
            String fName,
            String lName,
            String zip
            )
    	{
        WaitUtils.waitForVisible(firstName).sendKeys(fName);
        lastName.sendKeys(lName);
        postalCode.sendKeys(zip);
        continueBtn.click();

        return new CheckoutOverviewPage();
    }
}
