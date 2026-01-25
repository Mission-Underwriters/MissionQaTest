package saucedemo.ui.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author myname
 */
public class OrderConfirmationPage {

    WebDriver driver;

    public void OrderPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//h2[normalize-space()='Thank you for your order!']")
    WebElement confirmationMessage;


    // pubic String Object getConfirmationMwssage(){
     //  return confirmationMessage.getText();
   // }
}
