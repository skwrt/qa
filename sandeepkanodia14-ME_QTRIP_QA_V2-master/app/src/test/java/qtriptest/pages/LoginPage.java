package qtriptest.pages;

import qtriptest.*;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static org.testng.Assert.*;

public class LoginPage {

    WebDriver driver;

    @FindBy(xpath = "//input[@name='email']")
    WebElement username_input;
    @FindBy(xpath = "//input[@name='password']")
    WebElement password_input;
    @FindBy(xpath = "//button[@class='btn btn-primary btn-login']")
    WebElement login_button;
   


    public LoginPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void performLogin(String username, String password) throws InterruptedException{
        // username_input.sendKeys(username);
        SeleniumWrapper.sendKeys(username_input,username);
        // password_input.sendKeys(password);
        SeleniumWrapper.sendKeys(password_input,password);
        // login_button.click();
        SeleniumWrapper.click(login_button,driver);
        Thread.sleep(5000);
    }
}
