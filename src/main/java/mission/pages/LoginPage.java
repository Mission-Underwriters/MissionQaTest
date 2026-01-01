package mission.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import mission.base.BasePage;
import mission.utils.WaitUtils;

// LoginPage represents the login screen of the website.
public class LoginPage extends BasePage {
	public LoginPage(WebDriver driver) { super(driver); }

	private By errorMsg = By.cssSelector("[data-test='error']");   
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    // Logs into the application using the provided username and password.
    public HomePage login(String username, String password) {
    	WaitUtils.waitForElementVisible(driver, usernameField, 10);
    	usernameField.clear();
        usernameField.sendKeys(username);
        
        passwordField.clear();
        passwordField.sendKeys(password);
        
        loginButton.click();
        return new HomePage(driver);
    }
    
    /*public boolean isUserLoggedIn() {
        return driver.getCurrentUrl().contains("inventory");
    }*/
    
    public boolean isUserLoggedIn() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("inventory"),
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("shopping_cart_container")
                    )
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isErrorDisplayed() {
        return driver.findElements(errorMsg).size() > 0;
    }
}