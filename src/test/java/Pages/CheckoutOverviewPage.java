package Pages;

import Base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

/*
 * Purpose:
 * Represents the Checkout Overview page where
 * the user reviews the order before final submission.
 *
 * Dependencies:
 * Extends BasePage for shared WebDriver handling
 * Uses WaitUtils to ensure button is clickable
 * Navigates to CheckoutCompletePage after finishing checkout
 */
public class CheckoutOverviewPage extends BasePage {

    /* ================= Page Elements ================= */

    @FindBy(id = "finish")
    private WebElement finishBtn;

    // Constructor initializes overview page elements
    public CheckoutOverviewPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    /* ================= Actions ================= */

    // Completes the checkout process and moves to confirmation page
    public CheckoutCompletePage finishCheckout() {
        WaitUtils.waitForClickable(finishBtn).click();
        return new CheckoutCompletePage();
    }
}
