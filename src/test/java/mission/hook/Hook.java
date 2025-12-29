package mission.hook;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import mission.base.BrowserSetup;



public class Hook {

	public static WebDriver driver;

    @Before("@ui")
    public void setUp() {
    	driver = BrowserSetup.selectBrowser();
    }

    @After("@ui")
    public void tearDown() {
//    System.out.println("Finished execution successfully!");
        if (driver != null) {
            driver.quit();
        }
    }
    /*@Before("@api")
    public void beforeApiTest() {
        // Optionally, setup API base URI
        System.out.println("Starting API test...");
    }

    @After("@api")
    public void afterApiTest() {
        System.out.println("API test finished.");
    } */
    
    }