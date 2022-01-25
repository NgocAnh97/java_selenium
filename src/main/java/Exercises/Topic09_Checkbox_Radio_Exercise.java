package Exercises;

import javafx.scene.web.WebEngine;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

// Link docs:https://docs.google.com/document/d/1kPgRirztWIC9R_XiZFNYI3E0KVWfrzf2x_Het5MRj3s/edit#
public class Topic09_Checkbox_Radio_Exercise {
    WebDriver driver;
    String osName = System.getProperty("os.name");
    String projectPath = System.getProperty("user.dir") + "/driver";
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass(){
        if(osName.contains("Windows")){
            System.setProperty("webdriver.chrome.driver", projectPath + "\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        }
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.get("");
    }

    //@Test
    public void TC_01_Button_JSExecutor() {
        driver.get("https://www.fahasa.com/customer/account/create");
        driver.findElement(By.cssSelector("li[class*='tab-login']")).click();
        By btnLogin = By.cssSelector("button.fhs-btn-login");
        System.out.println("Before filling in valid fields, enabled: " + isElementEnabled(btnLogin));
        driver.findElement(By.id("login_username")).sendKeys("test@yopmail.com");
        driver.findElement(By.id("login_password")).sendKeys("123456");
        sleepInSecond(3);
        System.out.println("After filling in valid fields, enabled: " + isElementEnabled(btnLogin));
        Assert.assertEquals(driver.findElement(btnLogin).getCssValue("background-color"),"rgba(201, 33, 39, 1)");
        driver.navigate().refresh();
        driver.findElement(By.cssSelector("li[class*='tab-login']")).click();
        sleepInSecond(3);
        removeDisabledAttributeByJS(btnLogin);
        driver.findElement(btnLogin).click();

       WebElement alert = driver.findElement(By.cssSelector("div.popup-login-content .fhs-input-alert"));
       Assert.assertEquals(alert.getText(),"Thông tin này không thể để trống");
    }

    //@Test
    public void TC_02_Default_Checkbox(){
        driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
        By checkBox5 = By.cssSelector("input#eq5");
        driver.findElement(checkBox5).click();
        Assert.assertTrue(driver.findElement(checkBox5).isSelected());
        driver.findElement(checkBox5).click();
        Assert.assertFalse(driver.findElement(checkBox5).isSelected());

        driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
        By radio3 = By.cssSelector("input#engine3");
        driver.findElement(radio3).click();
        Assert.assertTrue(driver.findElement(radio3).isSelected());
    }


    @AfterClass
    public void afterClass(){
        driver.quit();
    }

    public void clickByJS(By by){
        WebElement element = driver.findElement(by);
        jsExecutor.executeScript("arguments[0].click()", element);
    }

    public boolean isElementEnabled(By by){
        WebElement element = driver.findElement(by);
        if (element.isEnabled()) {
            System.out.println("Element is enabled: " + by);
            return true;
        }else {
            System.out.println("Element is disable: "+ by);
            return false;
        }
    }
    public void removeDisabledAttributeByJS(By by){
        WebElement element = driver.findElement(by);
        jsExecutor.executeScript("arguments[0].removeAttribute('disabled')", element);
    }
    public void sleepInSecond(long second){
        try{
            Thread.sleep(second*1000);
        } catch  (InterruptedException e){
            e.printStackTrace();
        }
    }
}

