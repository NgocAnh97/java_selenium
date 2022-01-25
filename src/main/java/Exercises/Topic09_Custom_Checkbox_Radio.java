package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

// Link docs:https://docs.google.com/document/d/1kPgRirztWIC9R_XiZFNYI3E0KVWfrzf2x_Het5MRj3s/edit#
public class Topic09_Custom_Checkbox_Radio {
    WebDriver driver;
    String osName = System.getProperty("os.name");
    String projectPath = System.getProperty("user.dir") + "/driver";
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass(){
        if(osName.contains("Windows")){
            System.setProperty("webdriver.chrome.driver", projectPath + "\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        }
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test fail
    public void TC_01_Custom_Checkbox_2_Elements(){
        driver.get("https://material.angular.io/components/radio/examples");
        By summerInput = By.xpath("//input[@value='Summer']");
        By summerSpan = By.xpath("//input[@value='Summer']/preceding-sibling::span[@class='mat-radio-outer-circle']");
        // Case 1: The input de click; span de verify
        driver.findElement(summerSpan).click();
        sleepInSecond(5);
        Assert.assertTrue(driver.findElement(summerInput).isSelected());
    }

    //@Test
    public void TC_02_Custom_Checkbox_ByJS(){
        driver.get("https://material.angular.io/components/radio/examples");
        By summerInput = By.xpath("//input[@value='Summer']");
        clickByJS(summerInput);
        sleepInSecond(5);
        Assert.assertTrue(driver.findElement(summerInput).isSelected());
    }

    //@Test
    public void TC_03_Custom_Checkbox(){
        driver.get("https://material.angular.io/components/checkbox/examples");
        By checkedInput = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
        By indeterminateInput = By.xpath("//span[text()='Indeterminate']/preceding-sibling::span/input");

        // Check
        checkToCheckbox(checkedInput);
        checkToCheckbox(indeterminateInput);
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(checkedInput).isSelected());
        Assert.assertTrue(driver.findElement(indeterminateInput).isSelected());

        // Uncheck
        uncheckToCheckbox(checkedInput);
        uncheckToCheckbox(indeterminateInput);
        sleepInSecond(2);
        Assert.assertFalse(driver.findElement(checkedInput).isSelected());
        Assert.assertFalse(driver.findElement(indeterminateInput).isSelected());
    }

    @Test
    public void TC_04_Custom_Checkbox_GGDocs(){
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
        By canThoRadio = By.xpath("//div[@data-value = 'Cần Thơ']");
        // Before click
        Assert.assertEquals(driver.findElement(canThoRadio).getAttribute("aria-checked"), "false");
        driver.findElement(canThoRadio).click();
        sleepInSecond(5);
        // After click
        Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label = 'Cần Thơ' and @aria-checked ='true']")).isDisplayed());

        // Click 7 checkboxes
        List<WebElement> beforeCheckboxes = driver.findElements(By.xpath("//div[@role='checkbox']"));
        for(WebElement checkbox : beforeCheckboxes){
            checkbox.click();
            sleepInSecond(1);
            Assert.assertEquals(checkbox.getAttribute("aria-checked"), "true");
        }

//        List<WebElement> afterCheckboxes = driver.findElements(By.xpath("//div[@role='checkbox' and @aria-checked = 'true']"));
//        for(WebElement checkbox : afterCheckboxes){
//            Assert.assertTrue(checkbox.isDisplayed());
//        }
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

    public void checkToCheckbox(By by){
        WebElement element = driver.findElement(by);
        if(!element.isSelected()){
            jsExecutor.executeScript("arguments[0].click()", element);
        }
    }

    public void uncheckToCheckbox(By by){
        WebElement element = driver.findElement(by);
        if(element.isSelected()){
            jsExecutor.executeScript("arguments[0].click()", element);
        }
    }
    public void clickByJS(By by){
        WebElement element = driver.findElement(by);
            jsExecutor.executeScript("arguments[0].click()", element);
    }

    public boolean isElementEnabled(By by){
        WebElement element = driver.findElement(by);
        if (element.isEnabled()) {
            System.out.println("Element is enabled: " + by);
            return true;
        }else {
            System.out.println("Element is disable: "+ by);
            return false;
        }
    }
    public void removeDisabledAttributeByJS(By by){
        WebElement element = driver.findElement(by);
        jsExecutor.executeScript("arguments[0].removeAttribute('disabled')", element);
    }
    public void sleepInSecond(long second){
        try{
            Thread.sleep(second*1000);
        } catch  (InterruptedException e){
            e.printStackTrace();
        }
    }
}
