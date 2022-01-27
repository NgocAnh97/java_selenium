package Exercises;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

// Link exercise docs: https://docs.google.com/document/d/1IZn3_cDV95SRT7RuEl8NWjxw4IPmjwMpd38-HmXfgYM/edit#
public class Topic12_Windows_Tab {
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
        System.out.println("Driver ID: " + driver.toString());
        explicitWait = new WebDriverWait(driver,30);
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test
    public void TC_01_Only_2_Window() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        // Get ID of active tab:
        String parentTabID = driver.getWindowHandle();

        Assert.assertEquals(driver.getTitle(), "Google");

        // Switch to parent tab:
        driver.switchTo().window(parentTabID);
        sleepInSecond(2);
    }

    //@Test
    public void TC_02_Greater_Than_2_Window() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();

        // Switch to Google tab:
        switchToTabByTitle("Google");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");

        // Switch to parent tab:
        switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
        sleepInSecond(2);
        driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();

        // Switch to FB tab:
        switchToTabByTitle("Facebook – log in or sign up");
        sleepInSecond(2);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");

        // Switch to parent tab:
        switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
        sleepInSecond(2);
        driver.findElement(By.xpath("//a[text()='TIKI']")).click();
        switchToTabByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
        Assert.assertEquals(driver.getCurrentUrl(), "https://tiki.vn/");

        // Close all tabs except parent tab: NOT PASS
//        switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
//        String parentTabID = driver.getWindowHandle();
//        Set<String> allTabIDs= driver.getWindowHandles();
//        for (String id: allTabIDs){
//            if(!id.equals(parentTabID)) {
//                driver.close();
//                sleepInSecond(2);
//                break;
//            }
//        }

        switchToTabByTitle("SELENIUM WEBDRIVER FORM DEMO");
        sleepInSecond(2);
        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
    }

    //@Test
    public void TC_03_Windows() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//a[text()='Mobile']")).click();
        driver.findElement(By.xpath("//ul/li[1]//a[@class='link-compare']")).click();
        sleepInSecond(2);
        Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");

        driver.findElement(By.xpath("//ul/li[3]//a[@class='link-compare']")).click();
        sleepInSecond(2);
        Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The product Samsung Galaxy has been added to comparison list.");

        driver.findElement(By.cssSelector("button[title='Compare']")).click();

        // Switch to new window:
        switchToTabByTitle("Products Comparison List - Magento Commerce");
        sleepInSecond(2);
        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "COMPARE PRODUCTS");
        // Close window, switch to parent window:
        driver.findElement(By.cssSelector("button[title='Close Window']")).click();
        switchToTabByTitle("Mobile");
        sleepInSecond(2);

        driver.findElement(By.xpath("//a[text()='Clear All']")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        sleepInSecond(2);
        Assert.assertEquals(driver.findElement(By.cssSelector(".success-msg span")).getText(), "The comparison list was cleared.");
    }

    @Test
    public void TC_04_Window(){
        driver.get("https://dictionary.cambridge.org/vi/");
        driver.findElement(By.xpath("//span[text()='Đăng nhập']")).click();
        switchToTabByTitle("Login");
        sleepInSecond(3);
        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("div#login span[data-bound-to='loginID']")).getText(),"This field is required");
        driver.findElement(By.cssSelector("div#login input[name='username']")).sendKeys("automationfc.com@gmail.com");
        driver.findElement(By.cssSelector("div#login input[name='password']")).sendKeys("Automation000***");

        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        sleepInSecond(5);

        switchToTabByTitle("Cambridge Dictionary | Từ điển tiếng Anh, Bản dịch & Từ điển từ đồng nghĩa");
        sleepInSecond(3);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@amp-access='loggedIn']//span[text()='Automation FC']")).isDisplayed());
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
