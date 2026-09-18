package QKART_TESTNG;
import org.openqa.selenium.WebDriver;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersTestNG implements ITestListener {

    public static void takeScreenshot(WebDriver driver, String screenshotType, String description) {
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, description);
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTestStart(ITestResult result) {
        ITestContext context = result.getTestContext();

        WebDriver driver = (WebDriver) context.getAttribute("driver");

        takeScreenshot(driver, "StartTestCase", result.getMethod().getMethodName());
        
    }

    public void onTestSuccess(ITestResult result) {
        ITestContext context = result.getTestContext();

        WebDriver driver = (WebDriver) context.getAttribute("driver");

        takeScreenshot(driver, "TestSuccess", result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        ITestContext context = result.getTestContext();

        WebDriver driver = (WebDriver) context.getAttribute("driver");

        takeScreenshot(driver, "TestFailure", result.getMethod().getMethodName());
    }

    public void onTestSkipped(ITestResult result) {
        ITestContext context = result.getTestContext();

        WebDriver driver = (WebDriver) context.getAttribute("driver");

        takeScreenshot(driver, "TestSkipped", result.getMethod().getMethodName());
    }
}
