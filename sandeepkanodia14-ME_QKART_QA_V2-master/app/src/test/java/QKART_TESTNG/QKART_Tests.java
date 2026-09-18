package QKART_TESTNG;

import QKART_TESTNG.pages.Checkout;
import QKART_TESTNG.pages.Home;
import QKART_TESTNG.pages.Login;
import QKART_TESTNG.pages.Register;
import QKART_TESTNG.pages.SearchResult;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.openqa.selenium.WindowType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Listeners(ListenersTestNG.class)
public class QKART_Tests {

    static RemoteWebDriver driver;
    public static String lastGeneratedUserName;

     @BeforeSuite(alwaysRun = true)
    public static void createDriver(ITestContext context) throws MalformedURLException {
        // Launch Browser using Zalenium
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        System.out.println("createDriver()");
        context.setAttribute("driver", driver);
    }

    /*
     * Testcase01: Verify a new user can successfully register
     */
         @Test(
            dataProvider = "registrationData",
            dataProviderClass = TestData.class,
            priority = 1,
            groups = {"sanity"}
        )
         public void TestCase01(String username, String pw) throws InterruptedException {
        Boolean status;
        //  logStatus("Start TestCase", "Test Case 1: Verify User Registration", "DONE");
        //  takeScreenshot(driver, "StartTestCase", "TestCase1");

        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
         status = registration.registerUser(username, pw, true);
        assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        // login.navigateToLoginPage();
         status = login.PerformLogin(lastGeneratedUserName, pw);
         logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        assertTrue(status, "Failed to login with registered user");

        // Visit the home page and log out the logged in user
        Home home = new Home(driver);
        status = home.PerformLogout();

        //  logStatus("End TestCase", "Test Case 1: Verify user Registration : ", status
        //  ? "PASS" : "FAIL");
        //  takeScreenshot(driver, "EndTestCase", "TestCase1");
    }

    @Test(
        dataProvider = "registrationData",
        dataProviderClass = TestData.class,
        priority = 2,
        groups = {"sanity"}
    )
    public void TestCase02(String username, String pw) throws InterruptedException{
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean status;
         status = registration.registerUser(username, pw, true);
        assertTrue(status, "Failed to register new user");

        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        // login.navigateToLoginPage();
         status = login.PerformLogin(lastGeneratedUserName, pw);
         logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        assertTrue(status, "Failed to login with registered user");

        registration.navigateToRegisterPage();
        status = registration.registerUser(username, pw, false);
        logStatus("Test Step", "User Perform re-Registration: ", status ? "FAIL" : "PASS");
       assertFalse(status, "Failed to re-register existing user");

       // Visit the home page and log out the logged in user
       Home home = new Home(driver);
       home.PerformLogout();

        // logStatus("End TestCase", "Test Case 2: Verify user re-Registration : ", status
        // ? "FAIL" : "PASS");
        // takeScreenshot(driver, "EndTestCase", "TestCase2");


    }

    @Test(
        dataProvider = "searchData",
        dataProviderClass = TestData.class,
        priority = 3,
        groups = {"sanity"}
    )
    public void TestCase03(String productname) throws InterruptedException{
        Home home = new Home(driver);
        home.navigateToHome();
        Boolean status;
        status = home.searchForProduct(productname);
        assertTrue(status, "Failed to search for YONEX");
        // logStatus("End TestCase", "Test Case 3: Verify search functionality : ", status
        // ? "PASS" : "FAIL");
        // takeScreenshot(driver, "EndTestCase", "TestCase3");
    }

    @Test(
        dataProvider = "sizeChartData",
        dataProviderClass = TestData.class,
        priority = 4,
        groups = {"regression"}
    )
    public void TestCase04(String productname) throws InterruptedException{
        Home home = new Home(driver);
        home.navigateToHome();
        Boolean status;
        status = home.searchForProduct(productname);
        assertTrue(status,"Failed to search for Shoes");
        SearchResult searchresult = new SearchResult(home.getSearchResults().get(0));
        status = searchresult.verifySizeChartExists();
        assertTrue(status,"Failed to verify size chart exists");
        status = searchresult.openSizechart();
        assertTrue(status,"Failed to open size chart");
        status = searchresult.validateSizeChartContents(Arrays.asList(
            "Size",
            "UK/INDIA",
            "EU",
            "HEEL TO TOE"
        ), Arrays.asList(
            Arrays.asList("6", "6", "40", "9.8"),
            Arrays.asList("7", "7", "41", "10.2"),
            Arrays.asList("8", "8", "42", "10.6"),
            Arrays.asList("9", "9", "43", "11"),
            Arrays.asList("10", "10", "44", "11.5"),
            Arrays.asList("11", "11", "45", "12.2"),
            Arrays.asList("12", "12", "46", "12.6")
        ), driver);
        assertTrue(status, "Failed to validate size chart contents");
        status = searchresult.closeSizeChart(driver);
        assertTrue(status, "Failed to close size chart contents");
        // logStatus("End TestCase", "Test Case 4: Verify the existence of size chart for certain items and validate contents of size chart : ", status
        // ? "PASS" : "FAIL");
        // takeScreenshot(driver, "EndTestCase", "TestCase4");
        
    }
    @Test(
        dataProvider = "checkoutData",
        dataProviderClass = TestData.class,
        priority = 5,
        groups = {"sanity"}
    )
    public void TestCase05(String productname1,String productname2,String address) throws InterruptedException{
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean status;
         status = registration.registerUser("testUser", "abc@123", true);
        assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        // login.navigateToLoginPage();
         status = login.PerformLogin(lastGeneratedUserName, "abc@123");
         logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        assertTrue(status, "Failed to login with registered user");
        Home home = new Home(driver);
        home.navigateToHome();
        
        status = home.searchForProduct(productname1);
        assertTrue(status,"Failed to search for Shoes");

        status = home.addProductToCart(productname1);
        assertTrue(status,"failed to add shoes to cart");
        status = home.searchForProduct(productname2);
        assertTrue(status,"Failed to search for YONEX");
        status= home.addProductToCart(productname2);
        assertTrue(status,"failed to add YONEX to cart");

        Checkout checkout = new Checkout(driver);
        checkout.navigateToCheckout();
        status = checkout.addNewAddress(address);
        assertTrue(status,"failed to add new address");
        status = checkout.selectAddress(address);
        assertTrue(status,"failed to select address");
        status = checkout.placeOrder();
        home.PerformLogout();

        // logStatus("End TestCase", "Test Case 5: 	Verify that a new user can add multiple products in to the cart and Checkout : ", status
        // ? "PASS" : "FAIL");
        // takeScreenshot(driver, "EndTestCase", "TestCase5");
    }

    @Test(
        dataProvider = "cartData",
        dataProviderClass = TestData.class,
        priority = 6,
        groups = {"regression"}
    )
    public void TestCase06(String productname1, String productname2) throws InterruptedException{
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean status;
         status = registration.registerUser("testUser", "abc@123", true);
        assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        // login.navigateToLoginPage();
         status = login.PerformLogin(lastGeneratedUserName, "abc@123");
         logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        assertTrue(status, "Failed to login with registered user");
        Home home = new Home(driver);
        home.navigateToHome();
        
        status = home.searchForProduct(productname1);
        assertTrue(status,"Failed to search for Shoes");

        status = home.addProductToCart(productname1);
        assertTrue(status,"failed to add shoes to cart");

        status = home.verifyCartContents(Arrays.asList(productname1));
        assertTrue(status,"shoes not showing in cart");

        status = home.searchForProduct(productname2);
        assertTrue(status,"Failed to search for YONEX");
        status= home.addProductToCart(productname2);
        assertTrue(status,"failed to add YONEX to cart");
        status = home.verifyCartContents(Arrays.asList(productname1,productname2));
        assertTrue(status,"YONEX not showing in cart");

        home.PerformLogout();

        // logStatus("End TestCase", "Test Case 6: 		Verify that the contents of the cart can be edited : ", status
        // ? "PASS" : "FAIL");
        // takeScreenshot(driver, "EndTestCase", "TestCase6");
    }

    @Test(
        dataProvider = "quantityData",
        dataProviderClass = TestData.class,
        priority = 7,
        groups = {"sanity"}
    )
    public void TestCase07(String productname, int qty) throws InterruptedException{
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean status;
         status = registration.registerUser("testUser", "abc@123", true);
        assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        // login.navigateToLoginPage();
         status = login.PerformLogin(lastGeneratedUserName, "abc@123");
         logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        assertTrue(status, "Failed to login with registered user");
        Home home = new Home(driver);
        home.navigateToHome();
        
        status = home.searchForProduct(productname);
        assertTrue(status,"Failed to search for Sofa");

        status = home.addProductToCart(productname);
        assertTrue(status,"failed to add sofa to cart");

        status = home.changeProductQuantityinCart(productname, qty);
        assertTrue(status,"failed to change sofa quantity");

        Checkout checkout = new Checkout(driver);
        checkout.navigateToCheckout();
        status = checkout.addNewAddress("New York San Francisco Chicago Milan");
        assertTrue(status,"failed to add new address");
        status = checkout.selectAddress("New York San Francisco Chicago Milan");
        assertTrue(status,"failed to select address");
        status = checkout.placeOrder();
        status = checkout.verifyInsufficientBalanceMessage();
        assertTrue(status,"insufficient balance msg not shown");

        home.PerformLogout();


        // logStatus("End TestCase", "Test Case 7: 	Verify that insufficient balance error is thrown when the wallet balance is not enough : ", status
        // ? "PASS" : "FAIL");
        // takeScreenshot(driver, "EndTestCase", "TestCase7");
    }

    @Test(priority = 8,
    groups = {"regression"})
    public void TestCase08() throws InterruptedException{
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean status;
         status = registration.registerUser("testUser", "abc@123", true);
        assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        // login.navigateToLoginPage();
         status = login.PerformLogin(lastGeneratedUserName, "abc@123");
         logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        assertTrue(status, "Failed to login with registered user");
        Home home = new Home(driver);
        home.navigateToHome();
        
        status = home.searchForProduct("Shoes");
        assertTrue(status,"Failed to search for Shoes");

        status = home.addProductToCart("Shoes");
        assertTrue(status,"failed to add shoes to cart");

        status = home.verifyCartContents(Arrays.asList("Roadster Mens Running Shoes"));
        assertTrue(status,"shoes not showing in cart");

        String originalHandle= driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);

        Home home2=new Home(driver);
        home2.navigateToHome();

        status = home.verifyCartContents(Arrays.asList("Roadster Mens Running Shoes"));
        assertTrue(status,"shoes not showing in cart in new tab");

        driver.close();
        driver.switchTo().window(originalHandle);

        home.PerformLogout();
        // logStatus("End TestCase", "Test Case 8: Verify that a product added to a cart is available when a new tab is added : ", status
        // ? "PASS" : "FAIL");
        // takeScreenshot(driver, "EndTestCase", "TestCase8");
    }

    @Test(priority = 9,
    groups = {"regression"})
    public void TestCase09() throws InterruptedException{
        Home home = new Home(driver);
        home.navigateToHome();
        driver.findElementByXPath("//a[text()='Privacy policy']").click();
        String originalHandle = driver.getWindowHandle();
        for(String handle: driver.getWindowHandles()){
            if(handle.equals(originalHandle)){

            }
            else{
                driver.switchTo().window(handle);
            }
        }
        assertEquals(driver.getCurrentUrl(),"https://qkart-qa-web.labs.crio.do/privacy-policy","privacy policy page link not working");
        driver.close();
        driver.switchTo().window(originalHandle);
        driver.findElementByXPath("//a[text()='About us']").click();
        for(String handle: driver.getWindowHandles()){
            if(handle.equals(originalHandle)){

            }
            else{
                driver.switchTo().window(handle);
            }
        }
        assertEquals(driver.getCurrentUrl(),"https://qkart-qa-web.labs.crio.do/aboutus","about us page link not working");
        driver.close();
        driver.switchTo().window(originalHandle);
        
        // logStatus("End TestCase", "Test Case 9: Verify that privacy policy and about us links are working fine : ","PASS");
        // takeScreenshot(driver, "EndTestCase", "TestCase9");

    }

    @Test(
        dataProvider = "contactData",
        dataProviderClass = TestData.class,
        priority = 10,
        groups = {"regression"}
    )
    public void TestCase10(String name, String email, String msg) throws InterruptedException{
        Home home = new Home(driver);
        home.navigateToHome();
        driver.findElementByXPath("//p[text()='Contact us']").click();
        WebDriverWait wait = new WebDriverWait(driver, 30); 
        
        Boolean status = wait.until(driver1->{
            driver1.findElement(By.xpath("//div[@class='card-block']"));
            return true;
        });
        assertTrue(status,"contact us dialog not opening");

        driver.findElements(By.xpath("//div[@class='card-block']//input")).get(0).sendKeys(name);
        driver.findElements(By.xpath("//div[@class='card-block']//input")).get(1).sendKeys(email);
        driver.findElements(By.xpath("//div[@class='card-block']//input")).get(2).sendKeys(msg);

        status = driver.findElement(By.xpath("//div[@class='card-block']//button")).isEnabled();

        // logStatus("End TestCase", "Test Case 10: Verify that the contact us dialog works fine ",status
        // ? "PASS" : "FAIL");
        // takeScreenshot(driver, "EndTestCase", "TestCase10");
    }

    @Test(
        dataProvider = "adsData",
        dataProviderClass = TestData.class,
        priority = 11,
        groups = {"sanity"}
    )
    public void TestCase11(String productname, String address) throws InterruptedException{
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean status;
         status = registration.registerUser("testUser", "abc@123", true);
        assertTrue(status, "Failed to register new user");

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        // login.navigateToLoginPage();
         status = login.PerformLogin(lastGeneratedUserName, "abc@123");
         logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        assertTrue(status, "Failed to login with registered user");
        Home home = new Home(driver);
        home.navigateToHome();
        
        status = home.searchForProduct(productname);
        assertTrue(status,"Failed to search for Shoes");

        status = home.addProductToCart(productname);
        assertTrue(status,"failed to add shoes to cart");

        Checkout checkout = new Checkout(driver);
        checkout.navigateToCheckout();
        status = checkout.addNewAddress(address);
        assertTrue(status,"failed to add new address");
        status = checkout.selectAddress(address);
        assertTrue(status,"failed to select address");
        status = checkout.placeOrder();
        assertTrue(status,"order placed successfully");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        List<WebElement> iframes = driver.findElements(By.xpath("//iframe"));
        status=true;
        driver.switchTo().frame(iframes.get(0));
        List<WebElement> button = driver.findElements(By.xpath("//button")); 
        for(WebElement we: button){
            if(we.isEnabled()){

            }
            else{
                status=false;
            }
        }
        driver.switchTo().defaultContent();
        driver.switchTo().frame(iframes.get(1));
        button = driver.findElements(By.xpath("//button")); 
        for(WebElement we: button){
            if(we.isEnabled()){

            }
            else{
                status=false;
            }
        }
        driver.switchTo().defaultContent();
        assertTrue(status,"ad links not clickable");

        home.PerformLogout();

        // logStatus("End TestCase", "Test Case 11:Ensure that the Advertisement Links on the QKART page are clickable ",status
        // ? "PASS" : "FAIL");
        // takeScreenshot(driver, "EndTestCase", "TestCase11");

    }

    @AfterSuite
    public static void quitDriver() {
        System.out.println("quit()");
        driver.quit();
    }

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }

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
}

