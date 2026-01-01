package Base;

import org.openqa.selenium.WebDriver;

/*
 * Purpose:
 * This is a base page class for all UI page objects.
 * Common objects like WebDriver are initialized here
 * so that child page classes don’t repeat the same code.
 *
 * This class hides driver initialization logic
 * All page classes extend this base class
 */
public abstract class BasePage {

    protected WebDriver driver;

    // This Constructor initialize driver using DriverFactory
    protected BasePage() {
        this.driver = DriverFactory.getDriver();
    }
}
