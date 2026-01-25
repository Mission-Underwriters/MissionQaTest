package saucedemo.ui.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author myname
 */
public class CheckoutPage {
    ;

    public CheckoutPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@placeholder='First Name']")
    WebElement firstName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement lastName;

    @FindBy(xpath = "//input[@placeholder='Zip/Postal Code']")
    WebElement postalcode;

    @FindBy(id = "continue")
    WebElement continueBtn;


  //Enter details and click on continue
  public void enterDetails(String fName, String lName, String zip) {
      firstName.sendKeys(fName);
      lastName.sendKeys(lName);
      postalCode.sendKeys(zip);
      continueBtn.click();
  }
}
