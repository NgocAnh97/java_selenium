package testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestNG_06_Parameter_MultiBrowser {
    WebDriver driver;
    String driverPath = System.getProperty("user.dir") + "/driver";

    @Test(priority = 1)
    void logoTest() throws InterruptedException{
        System.setProperty("webdriver.firefox.driver",driverPath + "/geckodriver");
        driver = new FirefoxDriver();
        driver.get("https://reputa.vn/");
        WebElement logo = driver.findElement(By.xpath("//a[@class=\"navbar-brand\"]"));
        Assert.assertTrue(logo.isDisplayed(),"Logo is not displayed");
        Thread.sleep(5000);
    }

    @Test(priority = 2)
    void titleTest(){
        System.setProperty("webdriver.firefox.driver",driverPath + "/geckodriver");
        driver.get("https://reputa.vn/");
        String title = driver.getTitle();
        Assert.assertEquals(title,"Reputa - Hệ thống lắng nghe & Giám sát danh tiếng dành cho doanh nghiệp","Title is not matched");
    }

    @AfterTest
    void tearDown(){
        driver.quit();
    }
}
