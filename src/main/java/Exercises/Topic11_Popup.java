package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

// Link exercise docs: https://docs.google.com/document/d/1IZn3_cDV95SRT7RuEl8NWjxw4IPmjwMpd38-HmXfgYM/edit#
public class Topic11_Popup {
    WebDriver driver;
    WebDriverWait explicitWait;
    String osName = System.getProperty("os.name");
    String projectPath = System.getProperty("user.dir") + "/driver";

    @BeforeClass
    public void beforeClass(){
        if(osName.contains("Windows")){
            System.setProperty("webdriver.chrome.driver", projectPath + "\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        }
        driver = new ChromeDriver();
        explicitWait = new WebDriverWait(driver,30);
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test
    public void TC_01_Fixed_Popup() {
        driver.get("https://ngoaingu24h.vn/");
        WebElement loginPopup = driver.findElement(By.cssSelector("#modal-login-v1 .modal-content"));
        Assert.assertFalse(loginPopup.isDisplayed());
        driver.findElement(By.cssSelector("div#button-login-dialog")).click();
        sleepInSecond(2);
        Assert.assertTrue(loginPopup.isDisplayed());

        driver.findElement(By.cssSelector("input#account-input")).sendKeys("ab");
        driver.findElement(By.cssSelector("input#password-input")).sendKeys("ab");
        driver.findElement(By.cssSelector("button.btn-login-v1']")).click();

        sleepInSecond(2);
        Assert.assertEquals(driver.findElement(By.cssSelector("#modal-login-v1 div.error-login-panel")).getText(),"Tài khoản không tồn tại!");
    }

    @Test
    // By pass website
    public void TC_02_Random_Popup_In_DOM(){
        driver.get("https://blog.testproject.io/");

        By popup = By.className("exit-popup");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(popup));
        driver.findElement(By.cssSelector(".exit-popup .popup-close")).click();
        sleepInSecond(2);
        driver.findElement(By.cssSelector("#secondary .search-field")).sendKeys("Selenium");
//        List <WebElement> postTitle = driver.findElements(By.className("post-title"));
//       for (title : postTitle){
//           Assert.assertTrue(postTitle.contains("Selenium"));
//       }
    }

    //@Test
    public void TC_03_Random_Popup_In_DOM() {
        driver.get("https://vnk.edu.vn/");
        By popup = By.cssSelector(".thrv_wrapper.thrv-columns");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(popup));
        sleepInSecond(2);
        driver.findElement(By.cssSelector("svg.tcb-icon")).click();
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(popup));
    }

    //@Test
    public void TC_04_Random_Popup_Not_In_DOM() {
        driver.get("https://dehieu.vn/");
        By popup = By.cssSelector("div.popup-content");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(popup));
        sleepInSecond(2);
        driver.findElement(By.cssSelector("button#close-popup")).click();
        sleepInSecond(2);
    }

    //@Test
    public void TC_05_Random_Popup_Shopee_Shadow_UI() {
        driver.get("https://shopee.vn/");
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

    public void sleepInSecond(long second){
        try{
            Thread.sleep(second*1000);
        } catch  (InterruptedException e){
            e.printStackTrace();
        }
    }
}
