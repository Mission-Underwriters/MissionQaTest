package Base;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * Purpose:
 * This class is responsible for creating, managing and closing WebDriver instances.
 * It supports multiple browsers and handles thread-safe execution.
 *
 * Driver creation logic is hidden inside this class
 * Ensures one driver per thread for parallel execution
 */
public final class DriverFactory {

	// ThreadLocal ensures thread-safe WebDriver for parallel runs
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	// Private constructor to prevent object creation
	private DriverFactory() {
	}

	// Initializes driver based on browser name from TestData.properties file
	public static void initDriver(String browser) {

		if (browser == null || browser.trim().isEmpty()) {
			throw new RuntimeException("Browser name is null or empty");
		}

		browser = browser.toLowerCase().trim();
		WebDriver webDriver;

		if (browser.equals("chrome")) {

			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver(getChromeOptions(false));

		} else if (browser.equals("chromeheadless")) {

			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver(getChromeOptions(true));

		} else if (browser.equals("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			webDriver = new FirefoxDriver(getFirefoxOptions(false));

		} else if (browser.equals("firefoxheadless")) {

			WebDriverManager.firefoxdriver().setup();
			webDriver = new FirefoxDriver(getFirefoxOptions(true));

		} else if (browser.equals("edge")) {

			WebDriverManager.edgedriver().setup();
			webDriver = new EdgeDriver(getEdgeOptions());

		} else {
			throw new RuntimeException("Unsupported browser: " + browser);
		}

		webDriver.manage().window().setSize(new Dimension(1920, 1080));

		driver.set(webDriver);
	}

	/* ================= Browser Options ================= */

	// Chrome-specific options
	private static ChromeOptions getChromeOptions(boolean headless) {

		ChromeOptions options = new ChromeOptions();

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);

		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--disable-notifications");

		if (headless) {
			options.addArguments("--headless=new", "--window-size=1920,1080");
		}

		return options;
	}

	// Firefox-specific options
	private static FirefoxOptions getFirefoxOptions(boolean headless) {

		FirefoxOptions options = new FirefoxOptions();

		if (headless) {
			options.addArguments("-headless");
		}

		return options;
	}

	// Edge-specific options
	private static EdgeOptions getEdgeOptions() {

		EdgeOptions options = new EdgeOptions();
		options.addArguments("--disable-notifications");

		return options;
	}

	/* ================= Driver Management ================= */

	// Returns the current thread’s WebDriver instance
	public static WebDriver getDriver() {
		return driver.get();
	}

	// Closes browser and removes driver from ThreadLocal
	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
}
