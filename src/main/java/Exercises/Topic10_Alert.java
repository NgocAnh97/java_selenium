package Exercises;

import org.openqa.selenium.Alert;
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

import java.util.concurrent.TimeUnit;

// Link docs:https://docs.google.com/document/d/1kPgRirztWIC9R_XiZFNYI3E0KVWfrzf2x_Het5MRj3s/edit#
public class Topic10_Alert {
    WebDriver driver;
    Alert alert;
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
        explicitWait = new WebDriverWait(driver, 30);
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test
    public void TC_01_Accept_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        // Wait before switching to alert
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(),"I am a JS Alert");
        alert.accept();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(),"You clicked an alert successfully");
    }

    //@Test
    public void TC_02_Confirm_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        // Wait before switching to alert
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        sleepInSecond(2);
        Assert.assertEquals(alert.getText(),"I am a JS Confirm");
        alert.dismiss();
        Assert.assertEquals(driver.findElement(By.id("result")).getText(),"You clicked: Cancel");
    }

    //@Test
    public void TC_03_Prompt_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        // Wait before switching to alert
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(),"I am a JS prompt");
        String text = "Automation FC";
        alert.sendKeys(text);
        sleepInSecond(2);
        alert.accept();
        sleepInSecond(2);
        Assert.assertEquals(driver.findElement(By.id("result")).getText(),"You entered: " + text);
    }

    //@Test
    public void TC_04_Authentication_Alert(){
        String username ="admin";
        String password ="admin";
        driver.get("http://" + username +":" + password +"@the-internet.herokuapp.com/basic_auth");

        Assert.assertEquals(driver.findElement(By.cssSelector(".example p")).getText(),"Congratulations! You must have the proper credentials.");
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
        sleepInSecond(3);
    }

    @Test
    public void TC_05_Authentication_Alert(){
        String username ="admin";
        String password ="admin";
        driver.get("https://the-internet.herokuapp.com/");

        WebElement basicAuthLink = driver.findElement(By.xpath("//a[text() = 'Basic Auth']"));
        driver.get(getAuthenticationUrl(basicAuthLink.getAttribute("href"), username, password));

        Assert.assertEquals(driver.findElement(By.cssSelector(".example p")).getText(),"Congratulations! You must have the proper credentials.");
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
        sleepInSecond(3);
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

    public String getAuthenticationUrl(String url, String username, String password){
        String[] urlValues = url.split("//");
        url = urlValues[0] + username + ":" + password +"@" +urlValues[1];
        return url;
    }
    public void sleepInSecond(long second){
        try{
            Thread.sleep(second*1000);
        } catch  (InterruptedException e){
            e.printStackTrace();
        }
    }
}
