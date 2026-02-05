package saucedemo.ui.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;
    //Create constructor for driver initialization

    public void LoginPage(String username, String password){
        this.driver=driver;
        //Pagefactory
        PageFactory.initElements(driver,this);

    }
    //Username locator
    @FindBy(id="user-name")
    WebElement usernameInput;

    //Password locator
    @FindBy(id="password")
    WebElement passwordInput;

    //Login Button locator
    @FindBy(id ="login-button")
    WebElement loginButton;


    //Login  function
    public void LoginApplication(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

}


