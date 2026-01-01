package mission.hook;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import mission.base.BrowserSetup;
import mission.pages.CartPage;
import mission.pages.CheckoutPage;
import mission.pages.HomePage;
import mission.pages.LoginPage;

// Hook is responsible for setting up and tearing down the test environment for UI tests.
public class Hook {
	public static WebDriver driver;
	public static LoginPage loginPage;
	public static HomePage homePage;
	public static CartPage cartPage;
	public static CheckoutPage checkoutPage;

	// Open a browser and get all pages ready before the test starts
	@Before("@ui")
	public void setUp() {
		driver = BrowserSetup.selectBrowser();
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		cartPage = new CartPage(driver);
		checkoutPage = new CheckoutPage(driver);
	}

	// Clean up after the test is done by closing the browser
	@After("@ui")
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// Takes screenshot if any test fails
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