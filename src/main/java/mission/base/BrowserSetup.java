package mission.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import mission.utils.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.HashMap;
import java.util.Map;


// It is responsible for creating and configuring the browser we want to use for tests.
public class BrowserSetup {
	
	// It reads which browser to use from a configuration file and sets it up.
	public static WebDriver selectBrowser() {
		WebDriver driver;
		String browser = LoadProp.getProperty("Browser");
		String url = LoadProp.getProperty("url");
		switch (browser.toLowerCase()) {
		case "chrome":
		case "chromemac":
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();

			// Disable Google Password Manager popup
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("profile.password_manager_leak_detection", false);

			chromeOptions.setExperimentalOption("prefs", prefs);
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-infobars");

			driver = new ChromeDriver(chromeOptions);
			break;
		case "chromeheadless":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			driver = new ChromeDriver(options);
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			throw new RuntimeException("Browser not supported: " + browser);
		}
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}
}