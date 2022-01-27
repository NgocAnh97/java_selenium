package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

// Link exercise docs: https://docs.google.com/document/d/1tjHswRo2ovMrT20YCVXEfidTk0yK9OcR67BQ5kG7I3s/edit#
public class Topic13_JE_Pending {
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

    public void openUrl(String url) {
        jsExecutor.executeScript("window.location='" + url + "'");
    }

    public String executeForBrowser(String javascript) {
        return jsExecutor.executeScript(javascript).toString();
    }

    public boolean areExpectedInnerText(String expectedText) {
        // Get inner test of page by JE:
        String actualText = jsExecutor.executeScript("return document.documentElement.innerText;").toString();
        return actualText.contains(expectedText);
    }

    public void clickByJs(By by) {
        WebElement element = driver.findElement(by);
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public void scrollByJs(By by) {
        WebElement element = driver.findElement(by);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public String getElementValidationMessage(By by) {
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
                driver.findElement(by));
    }

    public void highlightElement(By by){
        jsExecutor.executeScript("arguments[0].style.border='2px groove green'", driver.findElement(by));
    }

    //@Test
    public void TC_01_JavascriptExecutor() {
        openUrl("http://live.techpanda.org/");
        String domain = executeForBrowser("return document.domain");
        Assert.assertEquals(domain, "live.techpanda.org");

        String urlPage = executeForBrowser("return document.URL");
        Assert.assertEquals(urlPage, "http://live.techpanda.org/");
        clickByJs(By.xpath("//a[text()='Mobile']"));
        sleepInSecond(2);

        //clickByJs(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button"));
        clickByJs(By.xpath("//ul/li[3]//button[@class='button btn-cart']"));
        sleepInSecond(2);


        Assert.assertTrue(areExpectedInnerText("Samsung Galaxy was added to your shopping cart."));
//        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(),"Samsung Galaxy was added to your shopping cart.");

        highlightElement(By.xpath("//a[text()='Customer Service']"));
        clickByJs(By.xpath("//a[text()='Customer Service']"));
        sleepInSecond(2);

        String titleCustomerService = executeForBrowser("return document.title");
        Assert.assertEquals(titleCustomerService, "Customer Service");

        scrollByJs(By.xpath("//span[text()='Newsletter']"));

        // Not pass:
        String emailId = "test" + getRandomNumber() + "@yopmail.com";
        // Input to textbox by JE:
        jsExecutor.executeScript("document.getElementById('newsletter').value='" + emailId + "'");
        clickByJs(By.cssSelector("button[title='Subscribe']"));
        sleepInSecond(2);

        Assert.assertTrue(areExpectedInnerText("Thank you for your subscription."));

        openUrl("http://demo.guru99.com/v4/");
        sleepInSecond(2);
        Assert.assertEquals(executeForBrowser("return document.domain"), "demo.guru99.com");
    }

    //@Test
    public void TC_02_Validation() {
        driver.get("https://automationfc.github.io/html5/index.html");
        driver.findElement(By.className("btn")).click();
        String nameTextbox = (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
                driver.findElement(By.className("input[name='fname']")));
        Assert.assertEquals(nameTextbox, "Please fill out this field.");

    }

    //@Test
    public void TC_03_Validation() {
        openUrl("https://login.ubuntu.com/");
        sleepInSecond(2);
        By byEmailText = By.id("id_email");
        WebElement loginBtn = driver.findElement(By.cssSelector("button[data-qa-id='login_button']"));

        loginBtn.click();
        Assert.assertEquals(getElementValidationMessage(byEmailText), "Please fill out this field.");

        driver.findElement(byEmailText).sendKeys("dfdf@fddf326732@3267%^&");
        loginBtn.click();
        sleepInSecond(2);

        if (driver.toString().contains("ChromeDrive")) {
            Assert.assertEquals(getElementValidationMessage(byEmailText), "A part following '@' should not contain the symbol '@'.");
        } else if (driver.toString().contains("FirefoxDrive")) {
            Assert.assertEquals(getElementValidationMessage(byEmailText), "Please enter an email address.");
        }
    }

    @Test
    public void TC_04_RemoveAttribute(){
        openUrl("http://demo.guru99.com/v4");
        sleepInSecond(2);
        By byEmailText = By.name("uid");
        By byPassText = By.name("password");
        By byLoginBtn = By.name("btnLogin");

        driver.findElement(byEmailText).sendKeys("mngr238966");
        driver.findElement(byPassText).sendKeys("emYmEqe");
        driver.findElement(byLoginBtn).click();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public int getRandomNumber() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(9999);
    }

    public void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
