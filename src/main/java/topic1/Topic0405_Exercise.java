package topic1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic0405_Exercise {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir") + "/driver";

    @Test
    public void TC_01_Chrome() {
        System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("ngoc anh");
        driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys("invalidEmail");
        driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys("invalidConfirmEmail");
        driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0901123456");
        driver.findElement(By.xpath("//input[@id='chkRight']")).click();

        driver.findElement(By.xpath("//div[@class='field_btn']/button")).click();
//        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        WebElement emailError = driver.findElement(By.xpath("//label[@id='txtEmail-error']"));
        WebElement confirmEmailError = driver.findElement(By.xpath("//label[@id='txtCEmail-error']"));
        Assert.assertEquals(emailError.getText(),"Vui lòng nhập email hợp lệ");
        Assert.assertEquals(confirmEmailError.getText(),"Email nhập lại không đúng");


        System.out.println("Error 1: " + emailError.getText());
        System.out.println("Error 2: " + confirmEmailError.getText());

        driver.quit();

    }
}
