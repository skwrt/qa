package qtriptest.tests;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;
import org.openqa.selenium.By;
import static org.testng.Assert.*;
import qtriptest.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.io.IOException;

import qtriptest.pages.*;

public class testCase_01 {

    public WebDriver driver;
    String lastGeneratedUserName;
    public ExtentReports report;

    @BeforeTest(alwaysRun = true)
    public void setup() throws MalformedURLException{
        driver = DriverSingleton.getDriver();
        report = ReportSingleton.getReport();
    }

    @Test(dataProvider = "testcasedata",
    dataProviderClass = qtriptest.DP.class,
    groups={"Login Flow"},
    priority=1
    )
    public void TestCase01(String username, String password) throws InterruptedException,IOException{
        ExtentTest test = report.startTest("TestName01");
        HomePage homepage = new HomePage(driver);
        homepage.clickRegister();
        boolean isRegisterPage = driver.getCurrentUrl().contains("/pages/register");
        assertTrue(isRegisterPage,"not on register page");
        RegisterPage registerpage = new RegisterPage(driver);
        registerpage.registerNewUser(username,password,password,true);
        lastGeneratedUserName = registerpage.lastGeneratedUserName;
        LoginPage loginpage = new LoginPage(driver);
        loginpage.performLogin(lastGeneratedUserName,password);
        WebElement logout_button = driver.findElement(By.xpath("//div[text()='Logout']"));
        logout_button.click();
        Thread.sleep(5000);
        boolean islogin_available; 
        try{
            driver.findElement(By.xpath("//a[text()='Login Here']"));
            islogin_available=true;
        }
        catch(Exception e){
            islogin_available=false;
        }
        assertTrue(islogin_available,"not logged out");
        
        test.log(LogStatus.PASS,test.addScreenCapture(ReportSingleton.capture(driver))+ "testcase 01 passed");
        report.endTest(test);

    }

    @AfterTest
    public void cleanup() throws MalformedURLException{
        DriverSingleton.quitDriver();
    }
    
}
