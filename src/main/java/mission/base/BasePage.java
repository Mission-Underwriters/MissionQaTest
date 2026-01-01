package mission.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/* A common parent for all pages in the website.*/
public abstract class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}