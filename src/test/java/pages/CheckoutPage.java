package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By zip = By.id("postal-code");
    By continueBtn = By.id("continue");

    By itemTotal = By.className("summary_subtotal_label");
    By tax = By.className("summary_tax_label");

    public void enterDetails(String f, String l, String z) {
        driver.findElement(firstName).sendKeys(f);
        driver.findElement(lastName).sendKeys(l);
        driver.findElement(zip).sendKeys(z);
    }

    public void clickContinue() {
        driver.findElement(continueBtn).click();
    }

    public double getItemTotal() {
        return Double.parseDouble(
                driver.findElement(itemTotal).getText().replace("Item total: $", "")
        );
    }

    public double getTax() {
        return Double.parseDouble(
                driver.findElement(tax).getText().replace("Tax: $", "")
        );
    }
}

