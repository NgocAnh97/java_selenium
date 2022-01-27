package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

// Link exercise docs: https://docs.google.com/document/d/1IZn3_cDV95SRT7RuEl8NWjxw4IPmjwMpd38-HmXfgYM/edit#
public class Topic12_Iframe_Frame {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    WebDriverWait explicitWait;
    String osName = System.getProperty("os.name");
    String projectPath = System.getProperty("user.dir") + "/driver";

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        jsExecutor = (JavascriptExecutor) driver;
        explicitWait = new WebDriverWait(driver, 30);
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test
    public void TC_01_IFrame() {
        driver.get("https://kyna.vn/");
        WebElement iframe = driver.findElement(By.xpath("//div[@class='face-content']/iframe"));

        // Switch to iframe FB
        driver.switchTo().frame(iframe);
        Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'likes')]")).getText(), "167K likes");

        // Switch to parent page
        driver.switchTo().defaultContent();

        // Switch to iframe chat
        driver.switchTo().frame("cs_chat_iframe");
        driver.findElement(By.cssSelector("div.button_bar")).click();
        driver.findElement(By.cssSelector("input.input_name")).sendKeys("test");
        driver.findElement(By.cssSelector("input.input_phone")).sendKeys("test");
        new Select(driver.findElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("HỖ TRỢ KỸ THUẬT");
        driver.findElement(By.cssSelector("textarea.input_textarea")).sendKeys("test");
        sleepInSecond(2);

        // Switch to parent page
        driver.switchTo().defaultContent();
        String keyword = "Excel";
        driver.findElement(By.id("live-search-bar")).sendKeys(keyword);
        driver.findElement(By.className("search-button")).click();
        sleepInSecond(3);

        List <WebElement> titles = driver.findElements(By.cssSelector(".content h4"));
        for (WebElement title : titles){
            System.out.println(title.getText().toLowerCase());
            // Ignore cases: upper, lower
            Assert.assertTrue(title.getText().toLowerCase().contains(keyword.toLowerCase()));
        }
//        Assert.assertTrue(titleExcel.contains("Excel"));
        Assert.assertEquals(driver.findElement(By.cssSelector(".menu-heading h1")).getText(),"'Excel'");
    }

    //@Test
    public void TC_02_Iframe(){
        driver.get("https://netbanking.hdfcbank.com/netbanking/");
        driver.switchTo().frame("login_page");
        driver.findElement(By.name("fldLoginUserId")).sendKeys("test");
        driver.findElement(By.cssSelector("a.login-btn ")).click();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(By.id("fldPasswordDispId")).isDisplayed());
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
