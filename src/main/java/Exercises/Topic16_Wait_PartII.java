package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

// Link document exercise: https://docs.google.com/document/d/1YHmFR2m0aPi29TQJm67_DAUXGUajm5qF7qpCdyACyxg/edit#
public class Topic16_Wait_PartII {
    WebDriver driver;
    WebDriverWait explicitWait;
    String osName = System.getProperty("os.name");
    String projectPath = System.getProperty("user.dir");
    String driverPath = System.getProperty("user.dir") + "/driver";
    By byConfirmEmail = By.xpath("//input[@name='reg_email_confirmation__']");


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
        explicitWait = new WebDriverWait(driver, 20);
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.facebook.com/");
    }

    @Test
    public void TC_01_Find_Element(){
        WebElement emailTextbox = driver.findElement(By.id("email"));
    }

    public void TC_01_Find_Elements(){
        List<WebElement> links = driver.findElements(By.tagName("a"));

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
