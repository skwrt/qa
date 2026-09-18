package qtriptest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.testng.Assert.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;


public class SeleniumWrapper {

    public static boolean click(WebElement elementToClick, WebDriver driver){
        assertTrue(elementToClick.isDisplayed(),"element does not exist");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", elementToClick);
        try {
            elementToClick.click();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean sendKeys(WebElement inputBox, String keysToSend){
        try{
            inputBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            inputBox.sendKeys(Keys.DELETE);
            inputBox.sendKeys(keysToSend);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public static boolean navigate(WebDriver driver , String url){
        if(!driver.getCurrentUrl().equals(url)){
        try{
            driver.get(url);
            return true;
        }
        catch(Exception e){
            return false;
        }}
        else{
            return true;
        }
    }

    public static WebElement findElementWithRetry(WebDriver driver , By by , int retryCount ){
        int count=0;
        while(count<retryCount){
            try{
                return driver.findElement(by);
            }
            catch(Exception e){
                count++;
            }
        }
        return null;
        
    }

}
