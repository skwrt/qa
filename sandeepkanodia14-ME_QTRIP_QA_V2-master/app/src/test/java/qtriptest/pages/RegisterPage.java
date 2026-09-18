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
import java.util.UUID;
import static org.testng.Assert.*;

public class RegisterPage {
    WebDriver driver;

    @FindBy(xpath = "//input[@name='email']")
    WebElement username_input;
    @FindBy(xpath = "//input[@name='password']")
    WebElement password_input;
    @FindBy(xpath = "//input[@name='confirmpassword']")
    WebElement retype_pw_input;
    @FindBy(xpath = "//button[@class='btn btn-primary btn-login']")
    WebElement register_button;

    public String lastGeneratedUserName;

    public RegisterPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void registerNewUser(String username, String password, String confirmPassword, boolean generateRandomUsername) throws InterruptedException{
        
        if (generateRandomUsername){
            username = username+UUID.randomUUID().toString();
        }
        
        // username_input.sendKeys(username);
        SeleniumWrapper.sendKeys(username_input,username);
        // password_input.sendKeys(password);
        SeleniumWrapper.sendKeys(password_input,password);
        // retype_pw_input.sendKeys(confirmPassword);.
        SeleniumWrapper.sendKeys(retype_pw_input,confirmPassword);
        // register_button.click();
        SeleniumWrapper.click(register_button,driver);
        Thread.sleep(5000);

        lastGeneratedUserName = username;

        boolean isLoginPage = driver.getCurrentUrl().contains("/pages/login");
        assertTrue(isLoginPage,"not on login page");
    }

}
