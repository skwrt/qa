package QKART_TESTNG;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        return new Object[][]{
            {"testUser", "abc@123"}
        };
    }

    @DataProvider(name = "searchData")
    public Object[][] searchData() {
        return new Object[][]{
            {"YONEX"}
        };
    }

    @DataProvider(name = "sizeChartData")
    public Object[][] sizeChartData() {
        return new Object[][]{
            {"Shoes"}
        };
    }

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() {
        return new Object[][]{
            {
                "Shoes",
                "YONEX",
                "New York San Francisco Chicago Milan"
            }
        };
    }

    @DataProvider(name = "cartData")
    public Object[][] cartData() {
        return new Object[][]{
            {
                "Roadster Mens Running Shoes",
                "YONEX Smash Badminton Racquet"
            }
        };
    }

    @DataProvider(name = "quantityData")
    public Object[][] quantityData() {
        return new Object[][]{
            {
                "Stylecon 9 Seater RHS Sofa Set",
                10
            }
        };
    }

    @DataProvider(name = "contactData")
    public Object[][] contactData() {
        return new Object[][]{
            {
                "crio user",
                "criouser@gmail.com",
                "testing"
            }
        };
    }

    @DataProvider(name = "adsData")
    public Object[][] adsData(){
        return new Object[][]{
            {
                "YONEX Smash Badminton Racquet",
                "addresss line1 line2 line3"
            }
        };
    }
}