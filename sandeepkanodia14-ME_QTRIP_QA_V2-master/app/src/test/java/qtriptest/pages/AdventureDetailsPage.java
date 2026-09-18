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


public class AdventureDetailsPage {

    WebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    WebElement name_input;

    @FindBy(xpath = "//input[@name='date']")
    WebElement date_input;

    @FindBy(xpath = "//input[@name='person']")
    WebElement person_input;

    @FindBy(xpath= "//button[@class='reserve-button']")
    WebElement submit;

    @FindBy(xpath= "//a[text()='Reservations']")
    WebElement reservation;

    @FindBy(xpath="//a[text()='Home']")
    WebElement home;


    public AdventureDetailsPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void bookAdventure(String guestName, String Date, String count) throws InterruptedException{
        // name_input.sendKeys(guestName);
        SeleniumWrapper.sendKeys(name_input,guestName);
        // date_input.sendKeys(Date);
        SeleniumWrapper.sendKeys(date_input,Date);
        // person_input.clear();
        // Thread.sleep(5000);
        // person_input.sendKeys(count);
        SeleniumWrapper.sendKeys(person_input,count);
        Thread.sleep(5000);
        // submit.click();
        SeleniumWrapper.click(submit,driver);
        Thread.sleep(5000);
    }

    public boolean isBookingSuccessful(){
        // try{
        //     driver.findElement(By.xpath("//*[contains(@class,'alert-success')]"));
        //     return true;
        // }
        // catch(Exception e){
        //     return false;
        // }
        if(
        SeleniumWrapper.findElementWithRetry(driver,By.xpath("//*[contains(@class,'alert-success')]"),3)!=null
        ){
            return true;
        }
        else{
            return false;
        }
    }

    public void getToHistory() throws InterruptedException{
        // reservation.click();
        SeleniumWrapper.click(reservation,driver);
        Thread.sleep(5000);
    }

    public void getToHome() throws InterruptedException{
        // home.click();
        SeleniumWrapper.click(home,driver);
        Thread.sleep(5000);
    }

}