package Topics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

// Link exercise docs:
public class Topic_Template {
    WebDriver driver;
    String osName = System.getProperty("os.name");
    String projectPath = System.getProperty("user.dir");
    String driverPath = System.getProperty("user.dir") + "/driver";


    @BeforeClass
    public void beforeClass(){
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", driverPath + "\\chromedriver.exe");
            System.setProperty("webdriver.firefox.driver", driverPath + "\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", driverPath + "/chromedriver");
            System.setProperty("webdriver.firefox.driver", driverPath + "/geckodriver");
        }
        driver = new FirefoxDriver();
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void TC_01_() {

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

