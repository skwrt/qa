package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.testng.Assert.*;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {

    private static WebDriver driver;

    // Private constructor prevents object creation
    private DriverSingleton() {
    }

    // Returns the single driver instance
    public static WebDriver getDriver() throws MalformedURLException {

        if (driver == null) {

            final DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(BrowserType.CHROME);
            driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
            System.out.println("createDriver()");

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }

        return driver;
    }

    // Closes browser and destroys the instance
    public static void quitDriver() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}