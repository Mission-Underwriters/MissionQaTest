package mission.hook;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
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
    
    @AfterStep("@ui")
    public void takeScreenshot(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            File screenshotDir = new File("Screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdir();
            }

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotDir, scenario.getName() + ".png");
            FileUtils.copyFile(srcFile, destFile);
        }
    }
    }