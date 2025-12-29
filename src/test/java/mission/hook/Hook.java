package mission.hook;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import mission.base.BrowserSetup;
import org.openqa.selenium.WebDriver;


public class Hook {

	public static WebDriver driver;

    @Before("@ui")
    public void setUp() {
    	driver = BrowserSetup.selectBrowser();
    }

    @After("@ui")
    public void tearDown() {
    System.out.println("Finished executuion successfully!");
        if (driver != null) {
            driver.quit();
        }
    }
    }