package Pages;

import Base.BasePage;
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/*
 * Purpose:
 * LoginPage represents the SauceDemo login screen.
 * It handles all login-related actions and validations.
 *
 * Extends BasePage to reuse WebDriver initialization
 * Uses WaitUtils to avoid hard waits
 */
public class LoginPage extends BasePage {

    // Page locators kept private to encapsulate page behavior
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login-button");
    private final By loginError = By.cssSelector("h3[data-test='error']");

    /*
     * Constructor:
     * Ensures browser always lands on the login page
     * Initializes page elements
     * Waits for the page to be fully ready before interaction
     */
    public LoginPage() {
        super();
        driver.get("https://www.saucedemo.com/"); // Always start from login page
        PageFactory.initElements(driver, this);
        WaitUtils.waitForPageLoad();
        WaitUtils.waitForVisible(username);
    }

    /*
     * Performs login using provided credentials.
     *
     * Behavior:
     * Skips empty username or password (used for negative scenarios)
     * Clicks login button after entering available values
     * Returns ProductPage only when login succeeds
     */
    public ProductPage login(String user, String pass) {

        if (user != null && !user.isEmpty()) {
            clearAndTypeUsername(user);
        }

        if (pass != null && !pass.isEmpty()) {
            clearAndTypePassword(pass);
        }

        WaitUtils.waitForClickable(loginBtn).click();

        // If error appears, remain on login page
        if (isLoginErrorDisplayed()) {
            return null;
        }

        return new ProductPage();
    }

    /* Clears and enters username value. */
    private void clearAndTypeUsername(String user) {
        WebElement element = WaitUtils.waitForVisible(username);
        element.clear();
        element.sendKeys(user);
    }

    /* Clears and enters password value. */
    private void clearAndTypePassword(String pass) {
        WebElement element = WaitUtils.waitForVisible(password);
        element.clear();
        element.sendKeys(pass);
    }

    /* Checks whether a login error message is displayed.  */
    public boolean isLoginErrorDisplayed() {
        try {
            return WaitUtils.waitForVisible(loginError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /* Returns the login error text shown on the UI. */
    public String getLoginErrorMessage() {
        return WaitUtils.waitForVisible(loginError).getText();
    }

    /* Verifies whether the login page is currently displayed.
       Used after logout or failed login validations.		*/
    public boolean isLoginPageDisplayed() {
        return WaitUtils.waitForVisible(username).isDisplayed()
                && WaitUtils.waitForVisible(loginBtn).isDisplayed();
    }
}
