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

public class HomePage {

    WebDriver driver;

    @FindBy(xpath = "//a[text()='Register']")
    WebElement register_button;

    @FindBy(xpath = "//input")
    WebElement search;

    @FindBy(xpath = "//*[@id='results']")
    WebElement search_result;
    
 
    public HomePage(WebDriver driver){
        this.driver=driver;
        // driver.get("https://qtrip-qa-web.labs.crio.do/");
        SeleniumWrapper.navigate(driver,"https://qtrip-qa-web.labs.crio.do/");
        PageFactory.initElements(driver, this);
    }

    public void clickRegister() throws InterruptedException{
        // register_button.click();
        SeleniumWrapper.click(register_button,driver);
        Thread.sleep(5000);
    }

    public void searchCity(String city) throws InterruptedException{
        // search.clear();
        // Thread.sleep(5000);
        // search.sendKeys(city);
        SeleniumWrapper.sendKeys(search,city);
        Thread.sleep(5000);
    }

    public boolean assertAutoCompleteText(String city){
        if(search_result.getText().contains(city)){
            return true;
        }
        else{
            return false;
        }
    }

    public void selectCity(String city) throws InterruptedException{
        // driver.findElement(By.xpath(String.format("//*[@id='results']//a[@id='%s']", city.toLowerCase()))).click();
        WebElement we = SeleniumWrapper.findElementWithRetry(driver, By.xpath(String.format("//*[@id='results']//a[@id='%s']", city.toLowerCase())), 3);
        SeleniumWrapper.click(we,driver);
        Thread.sleep(5000);
    }
}
