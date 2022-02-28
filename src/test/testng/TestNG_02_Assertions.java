package testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestNG_02_Assertions {
    WebDriver driver;
    String driverPath = System.getProperty("user.dir") + "/driver";

    @BeforeClass
    void setup(){
        System.setProperty("webdriver.firefox.driver",driverPath + "/geckodriver");
        driver = new FirefoxDriver();
        driver.get("https://reputa.vn/");
    }

    @Test(priority = 1)
    void logoTest(){
        WebElement logo = driver.findElement(By.xpath("//a[@class=\"navbar-brand\"]"));
        Assert.assertTrue(logo.isDisplayed(),"Logo is not displayed");
    }

    @Test(priority = 2)
    void titleTest(){
        String title = driver.getTitle();
        Assert.assertEquals(title,"Reputa - Hệ thống lắng nghe & Giám sát danh tiếng dành cho doanh nghiệp","Title is not matched");
    }

    @AfterClass
    void tearDown(){
        driver.quit();
    }

}
