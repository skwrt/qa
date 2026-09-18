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
import org.openqa.selenium.support.ui.Select;


public class AdventurePage {

    WebDriver driver;

    @FindBy(xpath = "//select[@id='duration-select']")
    WebElement duration_filter;

    @FindBy(xpath = "//select[@id='category-select']")
    WebElement category_filter;

    @FindBy(xpath = "(//div[@class='ms-3'])[1]")
    WebElement duration_clear;

    @FindBy(xpath = "(//div[@class='ms-3'])[2]")
    WebElement category_clear;

    
 
    public AdventurePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void setFilterValue(String filter) throws InterruptedException{
        Select duration_filter_select = new Select(duration_filter);
        duration_filter_select.selectByVisibleText(filter);
        Thread.sleep(5000);
    }

    public void setCategoryValue(String filter) throws InterruptedException{
        Select category_filter_select = new Select(category_filter);
        category_filter_select.selectByVisibleText(filter);
        Thread.sleep(5000);
    }

    public void clearFilterValue() throws InterruptedException{
        // duration_clear.click();
        SeleniumWrapper.click(duration_clear,driver);
        Thread.sleep(5000);
    }

    public void clearCategoryValue() throws InterruptedException{
        // category_clear.click();
        SeleniumWrapper.click(category_clear,driver);
        Thread.sleep(5000);
    }

    public int getResultCount(){
        return driver.findElements(By.xpath("//div[@class='activity-card']")).size();
    }

    public void selectAdventure(String adventure) throws InterruptedException{
        WebElement we = SeleniumWrapper.findElementWithRetry(driver, By.xpath(String.format("//div[@id='data']//*[contains(text(),'%s')]/ancestor::a",adventure)), 3);
        SeleniumWrapper.click(we,driver);
        Thread.sleep(5000);
    }

}