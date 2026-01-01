package mission.utils;

	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;

	import java.time.Duration;

	// WaitUtils is a helper class for waiting on web elements.
	public class WaitUtils {

		// Pause the test until this element actually appears on the screen
	    public static void waitForElementVisible(WebDriver driver, WebElement element, int timeout) {
	        new WebDriverWait(driver, Duration.ofSeconds(timeout))
	                .until(ExpectedConditions.visibilityOf(element));
	    }

	    // Pause the test until this element can actually be clicked.
	    public static void waitForElementClickable(WebDriver driver, WebElement element, int timeout) {
	        new WebDriverWait(driver, Duration.ofSeconds(timeout))
	                .until(ExpectedConditions.elementToBeClickable(element));
	    }
	}