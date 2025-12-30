package Base;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    protected WebDriver driver;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
    }
}
