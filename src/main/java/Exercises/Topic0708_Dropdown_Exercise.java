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
import java.util.Random;
import java.util.concurrent.TimeUnit;

// Link docs: https://docs.google.com/document/d/1QRI6jdKoCiMB3K7s16f3jEtAVHICdROpw_t30RD8gac/edit#heading=h.fusc17j2zjta
public class Topic0708_Dropdown_Exercise {
    WebDriver driver;
    WebDriverWait explicitWait;
    JavascriptExecutor jsExecutor;
    String osName = System.getProperty("os.name");
    String projectPath = System.getProperty("user.dir") + "/driver";
    Select select, dayDropdown, monthlyDropdown, yearDropdown, numberSelect;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//        Dimension dimension = new Dimension(720, 680);
//        driver.manage().window().setSize(dimension);
        jsExecutor = (JavascriptExecutor) driver;
        explicitWait = new WebDriverWait(driver, 30);
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.get("https://www.rode.com/wheretobuy");
    }

    //@Test
    public void TC_01_Default_Dropdown() {
        driver.get("https://www.rode.com/wheretobuy");
        select = new Select(driver.findElement(By.id("where_country")));
//        select.selectByValue("Vietnam");
        select.selectByVisibleText("Vietnam");
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");

        // Kiem tra dropdown co multiple
        Assert.assertFalse(select.isMultiple());
        Assert.assertEquals(select.getOptions().size(), 233);

        driver.findElement(By.name("search_loc_submit")).click();
        sleepInSecond(4);
        Assert.assertEquals(driver.findElement(By.cssSelector(".result_count span")).getText(), "32");

        List<WebElement> results = driver.findElements(By.cssSelector("#search_results .result_item .store_name"));
        for (WebElement result : results
        ) {
            System.out.println(result.getText());
        }
    }

    //@Test
    public void TC_02_HTML_Dropdown() {
        driver.get("https://demo.nopcommerce.com/register");
        driver.findElement(By.className("ico-register")).click();
        driver.findElement(By.id("gender-female")).click();
        driver.findElement(By.id("FirstName")).sendKeys("FirstName");
        driver.findElement(By.id("LastName")).sendKeys("LastName");

        dayDropdown = new Select(driver.findElement(By.name("DateOfBirthDay")));
        Assert.assertEquals(dayDropdown.getOptions().size(), 32);
        dayDropdown.selectByVisibleText("1");

        monthlyDropdown = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        Assert.assertEquals(monthlyDropdown.getOptions().size(), 13);
        monthlyDropdown.selectByVisibleText("May");

        yearDropdown = new Select(driver.findElement(By.name("DateOfBirthYear")));
        Assert.assertEquals(yearDropdown.getOptions().size(), 112);
        yearDropdown.selectByVisibleText("1980");

        driver.findElement(By.id("Email")).sendKeys("test" + getRandomNumber() + "@yopmail.com");
        driver.findElement(By.id("Password")).sendKeys("123456aA@@");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("123456aA@@");

        driver.findElement(By.id("register-button")).click();
        sleepInSecond(5);

        Assert.assertEquals(driver.findElement(By.className("result")).getText(), "Your registration completed");
        driver.findElement(By.className("ico-account")).click();

        // Road new page => gan lai du lieu moi
        dayDropdown = new Select(driver.findElement(By.name("DateOfBirthDay")));
        monthlyDropdown = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        yearDropdown = new Select(driver.findElement(By.name("DateOfBirthYear")));

        Assert.assertEquals(dayDropdown.getFirstSelectedOption().getText(), "1");
        Assert.assertEquals(monthlyDropdown.getFirstSelectedOption().getText(), "May");
        Assert.assertEquals(yearDropdown.getFirstSelectedOption().getText(), "1980");
    }

    @Test
    public void TC_03_Custom_Dropdown() {
        driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");

        selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li/div", "19");

        selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li/div", "4");

    }

    //@Test
    public void TC_04_ReactJS() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");

        selectItemInCustomDropdown("//i[@class='dropdown icon']", "//div[@role='option']/span", "Justen Kitsune");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='divider text' and text() = 'Justen Kitsune']")).isDisplayed());
    }

    //@Test
    public void TC_05_VueJS() {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");

        selectItemInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']/li/a", "Third Option");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), "Third Option");
    }

    //@Test
    public void TC_06_Angular() {
        driver.get("https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");

        selectItemInCustomDropdown("//ejs-dropdownlist[@ID='games']", "//ul[@id='games_options']/li", "Tennis");

        Assert.assertEquals(jsExecutor.executeScript("return document.querySelector('#games input').value;"), "Tennis");
    }

//    @Test
    public void TC_07_Angular() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        selectItemInCustomDropdown("//ng-select[@bindvalue='provinceCode']", "//span[contains(@class, 'ng-option-label')]", "Tỉnh Cao Bằng");
        Assert.assertEquals(driver.findElement(By.xpath("//ng-select[@bindvalue='provinceCode']//span[contains(@class,'ng-value-label ')]")).getText(), "Tỉnh Cao Bằng");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void selectItemInCustomDropdown(String parentXpath, String childXpath, String expectItemText) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(parentXpath)));

        /*// Not need jsExecutor scroll, click:
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", driver.findElement(By.xpath(parentXpath)));
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath(parentXpath)));*/

        driver.findElement(By.xpath(parentXpath)).click();
        sleepInSecond(2);
        // B2: Cho cho tat ca cac item con duoc presence co trong HTML DOM trong vong 30s (khoi tao beforeClass)
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

        // B3: Lay list item - lay den the con chua text (tranh loi du space,\n)
        List<WebElement> childItems = driver.findElements(By.xpath(childXpath));
        System.out.println("Tong so luong item trong dropdown: " + childItems.size());
        // B4: Duyet cac item (lap) → chon cai item can chon
        for (WebElement tempElement : childItems) {
            if (tempElement.getText().trim().equals(expectItemText)) {
                if (tempElement.isDisplayed()) {
                    System.out.println("Click by Selenium - " + tempElement.getText());
                    tempElement.click();
                    sleepInSecond(1);
                } else {
                    System.out.println("Click by Javascript - " + tempElement.getText());
                    // Scroll to element
                    WebElement temp = driver.findElement(By.xpath("//form[contains(@class,'ng-pristine')]"));
                    jsExecutor.executeScript("arguments[0].scrollIntoView(false);", temp);
                    sleepInSecond(2);
                    // Click by JavascriptExecutor
                    explicitWait.until(ExpectedConditions.elementToBeClickable(tempElement));
                    jsExecutor.executeScript("arguments[0].click();", tempElement);
                    sleepInSecond(2);
                }
                break;
            }
        }
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
