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


public class testCase_03 {

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
    groups={"Booking and Cancellation Flow"},
    priority=3)
    public void TestCase03(String NewUserName, String Password, String SearchCity, String AdventureName, String GuestName,
    String Date, String count) throws InterruptedException,IOException{
        ExtentTest test = report.startTest("TestName03");
        HomePage homepage = new HomePage(driver);
        homepage.clickRegister();
        RegisterPage registerpage = new RegisterPage(driver);
        registerpage.registerNewUser(NewUserName,Password,Password,true);
        lastGeneratedUserName = registerpage.lastGeneratedUserName;
        LoginPage loginpage = new LoginPage(driver);
        loginpage.performLogin(lastGeneratedUserName,Password);
        homepage.searchCity(SearchCity);
        homepage.selectCity(SearchCity);
        AdventurePage adventurepage = new AdventurePage(driver);
        adventurepage.selectAdventure(AdventureName);
        AdventureDetailsPage adventuredetails = new AdventureDetailsPage(driver);
        adventuredetails.bookAdventure(GuestName,Date,count);
        assertTrue(adventuredetails.isBookingSuccessful(),"booking not successful");
        adventuredetails.getToHistory();
        HistoryPage history = new HistoryPage(driver);
        for(String transaction_id : history.getTransactionId()){
            history.cancel(transaction_id);
        }
        driver.navigate().refresh();
        boolean istidremoved = history.getTransactionId().size()==0;
        assertTrue(istidremoved,"transaction id not removed");

        WebElement logout_button = driver.findElement(By.xpath("//div[text()='Logout']"));
        logout_button.click();
        Thread.sleep(5000);
        test.log(LogStatus.PASS,test.addScreenCapture(ReportSingleton.capture(driver))+ "testcase 03 passed");;
        report.endTest(test);
    }

    @ AfterTest
    public void cleanup() throws MalformedURLException{
        DriverSingleton.quitDriver();
    }
}
