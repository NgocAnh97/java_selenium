package Exercises;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

// Link docs: https://docs.google.com/document/d/1QRI6jdKoCiMB3K7s16f3jEtAVHICdROpw_t30RD8gac/edit#heading=h.fusc17j2zjta
public class Topic0708_Textarea_Exercise {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir") + "/driver";
    String userIDDemo, passwordDemo, customerID;
    String loginPageUrl;
    String registerPageUrl;
    String customerName, gender, date, addressInput, addressOutput, city, state, pin, telephone, emailid, password;

    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        driver = new ChromeDriver();

        //Ep kieu tuong minh
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        loginPageUrl = "http://demo.guru99.com/v4";
        registerPageUrl = "http://demo.guru99.com";

        customerName ="test";
        gender = "male";
        date = "1997-03-01";
        addressInput = "101\nVN";
        addressOutput = "101 VN";
        city = "HCM";
        state = "QTB";
        pin = "100000";
        telephone = "090123456";
        emailid = "test" + getRandomNumber() +"@yopmail.com";
        password = "123456";
    }

    @Test
    public void TC_01_Register() {
        driver.get(registerPageUrl);
        driver.findElement(By.cssSelector("[name='emailid']")).sendKeys("test" + getRandomNumber() +"@yopmail.com");
        driver.findElement(By.cssSelector("[name='btnLogin']")).click();

        Assert.assertTrue(driver.getPageSource().contains("Access details to demo site."));
        userIDDemo = driver.findElement(By.xpath("//td[text() = 'User ID :']/following-sibling::td")).getText();
        passwordDemo = driver.findElement(By.xpath("//td[text() = 'Password :']/following-sibling::td")).getText();
    }

    @Test
    public void TC_02_Login(){
        driver.get(loginPageUrl);
        driver.findElement(By.name("uid")).sendKeys(userIDDemo);
        driver.findElement(By.name("password")).sendKeys(passwordDemo);
        driver.findElement(By.name("btnLogin")).click();

        sleepInsecond(5);
        Assert.assertEquals(driver.findElement(By.cssSelector("marquee.heading3")).getText(),"Welcome To Manager's Page of Guru99 Bank");
        Assert.assertEquals(driver.findElement(By.cssSelector("tr.heading3>td")).getText(),"Manger Id : " + userIDDemo);
    }

    @Test
    public void TC_03_New_Customer(){
        driver.findElement(By.xpath("//a[text()='New Customer']")).click();

        driver.findElement(By.name("name")).sendKeys(customerName);

        WebElement dateTextbox = driver.findElement(By.name("dob"));
        // Remove attribute type -> sendKeys ok
        jsExecutor.executeScript("arguments[0].removeAttribute('type')", dateTextbox);
        sleepInsecond(3);   // to view
        dateTextbox.sendKeys(date);

        driver.findElement(By.name("addr")).sendKeys(addressInput);  // Textarea
        driver.findElement(By.name("city")).sendKeys(city);
        driver.findElement(By.name("state")).sendKeys(state);
        driver.findElement(By.name("pinno")).sendKeys(pin);
        driver.findElement(By.name("telephoneno")).sendKeys(telephone);
        driver.findElement(By.name("emailid")).sendKeys(emailid);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("sub")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("p.heading3")).getText(),"Customer Registered Successfully!!!");
        System.out.println("Customer ID: " + driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText());

        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), date);

        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), addressOutput);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), telephone);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), emailid);

        // Dung cho TC sau
        customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
    }

    @Test
    public void TC_04_Edit_Customer() {
        driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();  // Other xpath: //ul[@class='menusubnav']/li[3]

        driver.findElement(By.name("cusid")).sendKeys(customerID);
        driver.findElement(By.name("AccSubmit")).click();
        Assert.assertEquals(driver.findElement(By.name("name")).getAttribute("value"), customerName);
        Assert.assertEquals(driver.findElement(By.name("addr")).getText(), addressInput);
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

    public int getRandomNumber(){
        Random randomNumber = new Random();
        return randomNumber.nextInt(9999);
    }

    public void sleepInsecond(long second){
        try{
            Thread.sleep(second*1000);
        } catch  (InterruptedException e){
            e.printStackTrace();
        }
    }
}
