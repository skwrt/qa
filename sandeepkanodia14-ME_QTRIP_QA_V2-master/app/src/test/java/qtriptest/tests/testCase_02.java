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


public class testCase_02 {

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
    groups={"Search and Filter Flow"},
    priority=2
    )
    public void TestCase02(String CityName, String Category_Filter, String DurationFilter,String ExpectedFilteredResults, String ExpectedUnFilteredResults) throws InterruptedException,IOException{
        ExtentTest test = report.startTest("TestName02");
        HomePage homepage = new HomePage(driver);
        homepage.searchCity("Noida");
        boolean no_matches_found = driver.findElement(By.xpath("//*[@id='results']")).getText().contains("No City found");
        assertTrue(no_matches_found,"no matches found message not displayed");
        homepage.searchCity(CityName);
        assertTrue(homepage.assertAutoCompleteText(CityName),"city not displayed on autocomplete");
        homepage.selectCity(CityName);
        AdventurePage adventurepage = new AdventurePage(driver);
        adventurepage.setFilterValue(DurationFilter.trim());
        adventurepage.setCategoryValue(Category_Filter.trim());
        boolean ismatching = adventurepage.getResultCount()==Integer.valueOf(ExpectedFilteredResults);
        assertTrue(ismatching,"filtered results not matching");
        adventurepage.clearFilterValue();
        adventurepage.clearCategoryValue();
        ismatching= adventurepage.getResultCount()==Integer.valueOf(ExpectedUnFilteredResults);
        assertTrue(ismatching,"unfiltered results not matching");
        System.out.println("testcase02 passing");
        test.log(LogStatus.PASS,test.addScreenCapture(ReportSingleton.capture(driver))+ "testcase 02 passed");
        report.endTest(test);
    }

    @AfterTest
    public void cleanup() throws MalformedURLException{
        DriverSingleton.quitDriver();
    }

}
