package Exercises;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Link exercise docs: https://docs.google.com/document/d/15MqNX4HLiR29Vn2XhFhugTb2AJpAT16tiEGHzQ0GeFo/edit#
public class Topic10_User_Interaction {
    WebDriver driver;
    Actions action;
    Alert alert;
    JavascriptExecutor jsExecutor;
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
        jsExecutor = (JavascriptExecutor) driver;
        explicitWait = new WebDriverWait(driver, 30);

        action = new Actions(driver);
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test
    public void TC_01_UI_Hover() {
        driver.get("https://automationfc.github.io/jquery-tooltip/");
        WebElement inputAge = driver.findElement(By.id("age"));
        action.moveToElement(inputAge).perform();
        sleepInSecond(3);
        Assert.assertEquals(driver.findElement(By.cssSelector(".ui-tooltip-content")).getText(),"We ask for your age only for statistical purposes.");
    }

    //@Test
    public void TC_02_UI_Hover() {
        driver.get("http://www.myntra.com/");
        WebElement menuKids = driver.findElement(By.xpath("//div[@class='desktop-navLink']/a[text()='Kids']"));
        action.moveToElement(menuKids).perform();
        sleepInSecond(3);
        action.click(driver.findElement(By.xpath("//a[text()='Home & Bath']"))).perform();
        sleepInSecond(2);
        Assert.assertEquals(driver.findElement(By.cssSelector(".title-title")).getText(),"Kids Home Bath");
    }

    // Fail alert website ElementNotInteractableException: element not interactable
    //@Test
    public void TC_03_UI_Hover() {
        driver.get("https://www.fahasa.com/");
        By menuSachTrongNuoc = By.xpath("//div[contains(@class,'background-menu-homepage')]//span[text() ='Sách Trong Nước']");
        action.moveToElement(driver.findElement(menuSachTrongNuoc)).perform();
        sleepInSecond(3);
        action.click(driver.findElement(By.xpath("//div[contains(@class,'background-menu-homepage')]//a[text()='Kỹ Năng Sống']"))).perform();
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.xpath("//strong[text()='Kỹ Năng Sống']")).isDisplayed());
    }

    //@Test
    public void TC_04_UI_Click_And_Hold_Block() {
        driver.get("https://automationfc.github.io/jquery-selectable/");
        List <WebElement> rectangleNumbers = driver.findElements(By.cssSelector("ol#selectable li"));
        action.clickAndHold(rectangleNumbers.get(0))
                .moveToElement(rectangleNumbers.get(3)).release().perform();
        List<WebElement> rectangleNumbersSelected = driver.findElements(By.cssSelector("ol#selectable li[class$='ui-selected']"));
        Assert.assertEquals(rectangleNumbersSelected.size(),4);
    }

    //@Test
    public void TC_05_UI_Click_And_Hold_Random() {
        driver.get("https://automationfc.github.io/jquery-selectable/");
        List <WebElement> rectangleNumbers = driver.findElements(By.cssSelector("ol#selectable li"));

        action.keyDown(Keys.CONTROL).perform();
        sleepInSecond(3);
        action.click(rectangleNumbers.get(0))
                .click(rectangleNumbers.get(2))
                .click(rectangleNumbers.get(5))
                .click(rectangleNumbers.get(10)).perform();
        action.keyUp(Keys.CONTROL).perform();
        List<WebElement> rectangleNumbersSelected = driver.findElements(By.cssSelector("ol#selectable li[class$='ui-selected']"));
        Assert.assertEquals(rectangleNumbersSelected.size(),4);
    }

    //@Test
    public void TC_06_UI_Double_Click(){
        driver.get("https://automationfc.github.io/basic-form/index.html");
        // Firefox: not auto scroll:
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//button[text()='Double click me']")));
        action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
        Assert.assertEquals(driver.findElement(By.id("demo")).getText(),"Hello Automation Guys!");
    }

    //@Test
    public void TC_07_UI_Right_Click(){
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
        action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
        // Css . . contains multi text in class
        action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-hover.context-menu-visible")).isDisplayed());
        action.click(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
        sleepInSecond(2);

        // Wait before switching to alert
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
//        Assert.assertEquals(driver.switchTo().alert().getText(),"clicked: quit");
        Assert.assertEquals(alert.getText(),"clicked: quit");
        alert.accept();
        sleepInSecond(2);
        Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
    }

    @Test
    public void TC_08_Drag_And_Drop_HTML4(){
        driver.get("https://automationfc.github.io/kendo-drag-drop/");
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droptarget = driver.findElement(By.id("droptarget"));
        action.dragAndDrop(draggable, droptarget).perform();
        sleepInSecond(4);
        Assert.assertEquals(droptarget.getText(),"You did great!");
        String rgbColor = droptarget.getCssValue("background-color");
        String hexaColor = Color.fromString(rgbColor).asHex().toLowerCase();
        Assert.assertEquals(hexaColor,"#03a9f4");
    }

    //@Test
    public void TC_09_Drag_And_Drop_HTML5(){

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
