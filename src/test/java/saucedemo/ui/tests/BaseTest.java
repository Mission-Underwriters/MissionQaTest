package saucedemo.ui.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @author markadndey
 */
public class BaseTest {
    public WebDriver driver;
    @BeforeMethod
public  void setUp(){
        //Hande the password manager
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));
       driver =new chromeDriver(options);
       driver.manage().window().maximize();
       driver.get("https://www.saucedemo.com/");
}

@AfterMethod
public  void tearDown(){
        driver.quit();
}
    }
