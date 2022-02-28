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

import java.util.concurrent.TimeUnit;

// Link document exercise: https://docs.google.com/document/d/1YHmFR2m0aPi29TQJm67_DAUXGUajm5qF7qpCdyACyxg/edit#
public class Topic1516_Wait_PartI {
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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test
    public void TC_01_Visible() {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
        sleepInSecond(3);

        driver.findElement(By.xpath("//input[@name='reg_email__']")).sendKeys("test@yopmail.com");
        sleepInSecond(3);

        // Wait for element visible/displayed
        WebElement confirmEmailTextbox = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byConfirmEmail));
        Dimension confirmEmailSize = confirmEmailTextbox.getSize();
        System.out.println("Confirm height: " + confirmEmailSize.getHeight());
        System.out.println("Confirm width: " + confirmEmailSize.getWidth());
    }

    //@Test
    public void TC_02_Invisible() {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
        sleepInSecond(3);

        // Wait invisible
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byConfirmEmail));
    }

    //@Test
    public void TC_03_Presence_In_DOM() {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
        sleepInSecond(3);

        // Wait presence
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byConfirmEmail));
    }

    @Test
    public void TC_04_Stateless() {
        driver.get("https://www.facebook.com/");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testid='open-registration-form-button']"))).click();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).click();

        // Element confirm email in DOM
        WebElement confirmEmail = driver.findElement(byConfirmEmail);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img"))).click();

        // Wait confirm email stateless
        /*// Fail findElement because by element not in DOM:
        explicitWait.until(ExpectedConditions.stalenessOf(driver.findElement(byConfirmEmail)));*/
        explicitWait.until(ExpectedConditions.stalenessOf(confirmEmail));
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
