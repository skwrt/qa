package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    WebDriver driver;
    WebDriverWait wait;
    public Wrappers(WebDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void homePage() throws InterruptedException{
        driver.get("https://www.flipkart.com/");
        Thread.sleep(5000);
    }

    public void searchFor(String productname){
        WebElement searchbox=driver.findElement(By.xpath("//form[@class='lilxh_ header-form-search']//input[@title='Search for Products, Brands and More']"));
        searchbox.sendKeys(productname);
        searchbox.sendKeys(Keys.ENTER);
    }

    public void getRatingsCount(){
        driver.findElement(By.xpath("//div[text()='Popularity']")).click();
        List<WebElement> ratings=
        wait.until(driver->{
            try{
                List<WebElement> e = driver.findElements(By.xpath("//span[contains(@id,'productRating')]"));
                return e;
            }
            catch(Exception e){
                return null;
            }
        });
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        int count=0;
        for(int i=0;i<ratings.size();i++){
            float rating=Float.valueOf( driver.findElements(By.xpath("//span[contains(@id,'productRating')]")).get(i).getText());
            if(rating<=4.0){
                count+=1;
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        System.out.println("count of items with rating less than or equal 4 is: "+count);
    }

    public void titleWithDiscount(){
        List<WebElement> products=
        wait.until(driver->{
            try{
                List<WebElement> e = driver.findElements(By.xpath("//div[@class='ZFwe0M row']"));
                return e;
            }
            catch(Exception e){
                return null;
            }
        });
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        for(WebElement we:products){
            String title=we.findElement(By.xpath("//div[@class='RG5Slk']")).getText();
            String discount_string= we.findElement(By.xpath("//div[@class='HQe8jr']")).getText();
            float discount = Float.valueOf(discount_string.substring(0, discount_string.indexOf("%")));
            if(discount>=17.0){
                System.out.println("Title: "+title+" ,Discount: "+discount);
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    public void highestReviews() throws InterruptedException{
        driver.findElement(By.xpath("//div[starts-with(@title,'4')]")).click();
        Thread.sleep(5000);
        List<WebElement> products=
        wait.until(driver->{
            try{
                List<WebElement> e = driver.findElements(By.xpath("//div[@class='RGLWAk']"));
                return e;
            }
            catch(Exception e){
                return null;
            }
        });
        class Product {
            String title;
            String imageUrl;
            int numberOfRatings;

            Product(String title, String imageUrl, int numberOfRatings) {
                this.title = title;
                this.imageUrl = imageUrl;
                this.numberOfRatings = numberOfRatings;
            }
        }
        List<Product> products_list = new ArrayList<Product>();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        for(WebElement we:products){
            String title=we.findElement(By.xpath(".//div[@class='lWX0_T']//img")).getAttribute("alt");
            String image_url= we.findElement(By.xpath(".//div[@class='lWX0_T']//img")).getAttribute("src");
            int numberOfRatings;
            try{
                String number_of_Ratings = we.findElement(By.xpath(".//span[@class='PvbNMB']")).getText();
                numberOfRatings=Integer.valueOf(number_of_Ratings.substring(1,number_of_Ratings.indexOf(")")));
            }
            catch(Exception e){
                numberOfRatings=0;
            }
            products_list.add(new Product(title, image_url, numberOfRatings));
        }
        products_list.sort(
            Comparator.comparingInt((Product p) -> p.numberOfRatings).reversed()
        );
        List<Product> top5 = products_list.subList(0, Math.min(5, products.size()));
        for(Product p: top5){
            System.out.println("Title: "+p.title+ " imageUrl: "+p.imageUrl);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }
}
