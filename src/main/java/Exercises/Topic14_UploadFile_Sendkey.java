package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

// Link exercise docs: https://docs.google.com/document/d/1If4zUnKjRCy7L26G4mlN4YFKdOJnK71SM4JOenygXvs/edit#
public class Topic14_UploadFile_Sendkey {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String separatorChar = File.separator;
    String uploadFolderLocation = projectPath + separatorChar + "uploadFiles" + separatorChar;

    // Image File: verify
    String driverPath = System.getProperty("user.dir") + "/driver";
    String screen1 = "Screen1.png";
    String screen2 = "Screen2.png";
    String screen3 = "Screen3.png";

    // Image Location: senkey
    String screen1Location = uploadFolderLocation + screen1;
    String screen2Location = uploadFolderLocation + screen2;
    String screen3Location = uploadFolderLocation + screen3;


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
        driver.manage().window().maximize();
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test
    public void TC_01_UploadFile_Sendkeys() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        // Load file
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(screen1Location);
        sleepInSecond(1);
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(screen2Location);
        sleepInSecond(1);
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(screen3Location);
        sleepInSecond(1);

        // Uploading
        //driver.findElement(By.xpath("//span[text()='Start upload']")).click();
        // Method 2:
        List<WebElement> startBtns = driver.findElements(By.xpath("//span[text()='Start']"));
        for (WebElement start : startBtns) {
            start.click();
            sleepInSecond(1);
        }

        // Verify uploaded success
        Assert.assertTrue(driver.findElement(By.cssSelector(".name a[title='" + screen1 + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector(".name a[title='" + screen2 + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector(".name a[title='" + screen3 + "']")).isDisplayed());

    }

    @Test
    public void TC_02_Multiple_Files_Sendkeys() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        // Load file multiple files
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(screen1Location + "\n" + screen2Location + "\n" + screen3Location);
        sleepInSecond(1);

        // Uploading
        // Method 1:
        driver.findElement(By.xpath("//span[text()='Start upload']")).click();
        // Method 2:
//        List<WebElement> startBtns = driver.findElements(By.xpath("//span[text()='Start']"));
//        for (WebElement start : startBtns) {
//            start.click();
//            sleepInSecond(1);
//        }

        // Verify uploaded success
        Assert.assertTrue(driver.findElement(By.cssSelector(".name a[title='" + screen1 + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector(".name a[title='" + screen2 + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector(".name a[title='" + screen3 + "']")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    // Only work for only 2 tabs:
    public void switchExceptTab(String exceptTab) {
        // Get all tab IDs:
        Set<String> allTabIDs = driver.getWindowHandles();
        for (String id : allTabIDs) {
            if (!id.equals(exceptTab)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    // Work for all cases:
    public void switchToTabByTitle(String expectedTitle) {
        Set<String> allTabIDs = driver.getWindowHandles();
        for (String id : allTabIDs) {
            // Switch to tab -> check title:
            driver.switchTo().window(id);
            String actualTitle = driver.getTitle();
            if (actualTitle.equals(expectedTitle)) {
                break;
            }
        }
    }

    public void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
