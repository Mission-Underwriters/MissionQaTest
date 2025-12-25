package mission.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterDetails(String first, String last, String zip) {
        driver.findElement(By.id("first-name")).sendKeys(first);
        driver.findElement(By.id("last-name")).sendKeys(last);
        driver.findElement(By.id("postal-code")).sendKeys(zip);
        driver.findElement(By.id("continue")).click();
    }

    public double getItemTotal() {
        return Double.parseDouble(driver.findElement(By.className("summary_subtotal_label")).getText().replace("Item total: $", ""));
    }

    public double getTax() {
        return Double.parseDouble(driver.findElement(By.className("summary_tax_label")).getText().replace("Tax: $", ""));
    }
}