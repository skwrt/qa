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

public class testCase_04 {

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
    groups={"Reliability Flow"},
    priority=4
    )
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2, String dataset3) throws InterruptedException,IOException{
        ExtentTest test = report.startTest("TestName04");
        HomePage homepage = new HomePage(driver);
        homepage.clickRegister();
        RegisterPage registerpage = new RegisterPage(driver);
        registerpage.registerNewUser(NewUserName,Password,Password,true);
        lastGeneratedUserName = registerpage.lastGeneratedUserName;
        LoginPage loginpage = new LoginPage(driver);
        loginpage.performLogin(lastGeneratedUserName,Password);
        homepage.searchCity(dataset1.split(";")[0]);
        homepage.selectCity(dataset1.split(";")[0]);
        AdventurePage adventurepage = new AdventurePage(driver);
        adventurepage.selectAdventure(dataset1.split(";")[1]);
        AdventureDetailsPage adventuredetails = new AdventureDetailsPage(driver);
        adventuredetails.bookAdventure(dataset1.split(";")[2],dataset1.split(";")[3],dataset1.split(";")[4]);
        assertTrue(adventuredetails.isBookingSuccessful(),"booking not successful");
        adventuredetails.getToHome();

        homepage.searchCity(dataset2.split(";")[0]);
        homepage.selectCity(dataset2.split(";")[0]);
        adventurepage = new AdventurePage(driver);
        adventurepage.selectAdventure(dataset2.split(";")[1]);
        adventuredetails = new AdventureDetailsPage(driver);
        adventuredetails.bookAdventure(dataset2.split(";")[2],dataset2.split(";")[3],dataset2.split(";")[4]);
        assertTrue(adventuredetails.isBookingSuccessful(),"booking not successful");
        adventuredetails.getToHome();

        homepage.searchCity(dataset3.split(";")[0]);
        homepage.selectCity(dataset3.split(";")[0]);
        adventurepage = new AdventurePage(driver);
        adventurepage.selectAdventure(dataset3.split(";")[1]);
        adventuredetails = new AdventureDetailsPage(driver);
        adventuredetails.bookAdventure(dataset3.split(";")[2],dataset3.split(";")[3],dataset3.split(";")[4]);
        assertTrue(adventuredetails.isBookingSuccessful(),"booking not successful");
        
        adventuredetails.getToHistory();
        HistoryPage history = new HistoryPage(driver);
        boolean booking_displayed = history.getTransactionId().size()==3;
        assertTrue(booking_displayed,"all bookings not shown on history page");

        WebElement logout_button = driver.findElement(By.xpath("//div[text()='Logout']"));
        logout_button.click();
        Thread.sleep(5000);
        test.log(LogStatus.PASS,test.addScreenCapture(ReportSingleton.capture(driver))+ "testcase 04 passed");
        report.endTest(test);
    }

    @ AfterTest
    public void cleanup() throws MalformedURLException{
        DriverSingleton.quitDriver();
        report.flush();
    }

}
