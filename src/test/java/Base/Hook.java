package Base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/*
 * Purpose:
 * This Hooks class manages test setup and teardown
 * for UI scenarios using Cucumber annotations.
 *
 * It is responsible for:
 * - Launching the browser before each scenario
 * - Closing the browser after execution
 * - Capturing screenshots when a scenario fails
 *
 * It Uses DriverFactory for driver lifecycle
 * It Uses LoadProp for browser and URL configuration
 */
public class Hook {

    /*
     * Executed before every Cucumber scenario.
     * Initializes the WebDriver and navigates to the application URL.
     */
    @Before("@ui")
    public void setUp() {
        DriverFactory.initDriver(LoadProp.getBrowser());
        DriverFactory.getDriver().get(LoadProp.getUrl());
    }

    /*
     * Executed after every Cucumber scenario.
     * Captures screenshot on failure and quits the driver.
     */
    @After("@ui")
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            captureScreenshot(); // Helps debugging failed UI scenarios
        }

        DriverFactory.quitDriver();
    }

    /*
     * Captures a screenshot from the current browser instance.
     * This method is intentionally kept private as it is used
     * only inside this Hooks class.
     */
    private byte[] captureScreenshot() {
        return ((TakesScreenshot) DriverFactory.getDriver())
                .getScreenshotAs(OutputType.BYTES);
    }
}
