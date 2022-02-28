package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

// Link document exercise: https://docs.google.com/document/d/1YHmFR2m0aPi29TQJm67_DAUXGUajm5qF7qpCdyACyxg/edit#
public class Topic16_Wait_PartIII {
    WebDriver driver;
    WebDriverWait explicitWait;
    String osName = System.getProperty("os.name");
    String projectPath = System.getProperty("user.dir");
    String driverPath = System.getProperty("user.dir") + "/driver";

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", driverPath + "\\chromedriver.exe");
            System.setProperty("webdriver.firefox.driver", driverPath + "\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", driverPath + "/chromedriver");
            System.setProperty("webdriver.firefox.driver", driverPath + "/geckodriver");
        }
        driver = new FirefoxDriver();
        explicitWait = new WebDriverWait(driver, 15);
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
    }

    @Test
    public void TC_01_Visible() {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        WebElement startBtn = driver.findElement(By.xpath("//button[text()='Start']"));
        startBtn.click();

//        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#finish h4")));
        Assert.assertTrue(driver.findElement(By.cssSelector("#finish h4")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.cssSelector("#finish h4")).getText(), "Hello World!");
    }

    //@Test
    public void TC_02_Date_Picker() {
        driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.RadCalendar")));
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='1']")));
        driver.findElement(By.xpath("//a[text()='1']")).click();

        // Wait loading icon invisible
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id$='RadCalendar1']>div.raDiv")));
        // Wait element visible
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='1']")));

        Assert.assertTrue(driver.findElement(By.xpath("//td[@class='rcSelected' and @title='Tuesday, February 01, 2022']")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1")).getText(), "Tuesday, February 1, 2022");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
