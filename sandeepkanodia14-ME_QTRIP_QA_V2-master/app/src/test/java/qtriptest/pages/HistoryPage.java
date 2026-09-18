
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
import java.util.*;


public class HistoryPage {

    WebDriver driver;

    @FindBy(xpath = "//a[text()='Register']")
    WebElement register_button;

    @FindBy(xpath = "//input")
    WebElement search;

    @FindBy(xpath = "//*[@id='results']")
    WebElement search_result;

    @FindBy(xpath = "//tbody//th")
    List<WebElement> tids;
    
 
    public HistoryPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> getTransactionId(){
        List<String> ids= new ArrayList<String>();
        for(WebElement we:tids){
            ids.add(we.getText());
        }
        return ids;
    }

    public void cancel(String tid) throws InterruptedException{
        WebElement we = SeleniumWrapper.findElementWithRetry(driver, By.xpath(String.format("//button[@id='%s']",tid)), 3);
        SeleniumWrapper.click(we,driver);
        Thread.sleep(5000);
    }

}