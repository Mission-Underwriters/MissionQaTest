package utils;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import Base.DriverFactory;

/*
 * Purpose:
 * Centralized utility class for handling explicit and fluent waits.
 * 
 * Provide consistent, reusable wait logic
 *
 * All waits use the driver instance from DriverFactory
 * to ensure thread safety in parallel execution.
 */
public class WaitUtils {

    // Default explicit wait timeout (in seconds)
    private static final int TIMEOUT = 10;

    // Polling interval used for FluentWait (in milliseconds)
    private static final int POLLING = 500;

    /*
     * Fetches the current WebDriver instance
     * from DriverFactory (ThreadLocal safe).
     */
    private static WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    /* =================================================
       VISIBILITY WAITS
       Used when element must be present AND visible
       ================================================= */

    public static WebElement waitForVisible(By locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisible(WebElement element) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /* =================================================
       CLICKABLE WAITS
       Used before clicking buttons or links
       ================================================= */

    public static WebElement waitForClickable(By locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickable(WebElement element) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /* =================================================
       PAGE LOAD WAIT
       Ensures DOM is fully loaded before interaction
       ================================================= */

    public static void waitForPageLoad() {
        new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT))
                .until(driver ->
                        ((JavascriptExecutor) driver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }

    /* =================================================
       SMART / CUSTOM WAIT
       Used for dynamic or non-standard conditions
       ================================================= */

    public static void waitUntil(Function<WebDriver, Boolean> condition, int timeoutSeconds) {
        new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(POLLING))
                .ignoring(Exception.class)
                .until(condition);
    }

    /*
     * Utility wait to pause execution for a fixed duration.
     * Internally uses FluentWait to avoid Thread.sleep.
     */
    public static void waitForSeconds(int seconds) {
        waitUntil(driver -> true, seconds);
    }
}
