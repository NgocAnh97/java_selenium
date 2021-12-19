package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

// Link docs: https://docs.google.com/document/d/1QRI6jdKoCiMB3K7s16f3jEtAVHICdROpw_t30RD8gac/edit#heading=h.fusc17j2zjta
public class Topic0708_Textarea_Exercise {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir") + "/driver";

    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://demo.guru99.com/v4");
    }


    @Test
    public void TC_01_Textarea() {

        driver.findElement(By.cssSelector("[name='uid']")).sendKeys("mngr233468");
        driver.findElement(By.cssSelector("[name='password']")).sendKeys("tYqAhaq");
        driver.findElement(By.cssSelector("[name='btnLogin']")).click();

        driver.get("http://demo.guru99.com/");
        driver.findElement(By.cssSelector("[name='emailid']")).sendKeys("tesvtel@gmail.com");
        driver.findElement(By.cssSelector("[name='btnLogin']")).click();



        Assert.assertEquals(driver.getCurrentUrl(),"http://demo.guru99.com/v4/");

        driver.findElement(By.cssSelector(".buttons-set a")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/create/");
        driver.quit();
    }
}
