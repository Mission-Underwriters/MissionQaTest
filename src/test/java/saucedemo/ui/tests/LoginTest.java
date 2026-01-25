package saucedemo.ui.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.ui.pageObjects.LoginPage;

/**
 * @author myname
 */
public class LoginTest extends  BaseTest {
    @Test
     public  void loginWithValidCredentials(){
        LoginPage login= new LoginPage();
        login.LoginApplication("standard_user", "secret_sauce");
    }

    @Test
    public  void loginWithInvalidCredentials(){
        LoginPage login= new LoginPage();
        login.LoginApplication("standard_user23", "secret_sauce44");
         WebElement errorMsg =driver.findElement(By.className("error-button"));
        Assert.assertTrue(errorMsg.isDisplayed());
    }


}
