package topic1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Topic_01_Multi_Browse {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir") + "/driver";

//    @Test
//    public void TC_01_Firefox() {
//        System.setProperty("webdriver.gecko.driver", projectPath + "/geckodriver");
//        driver = new FirefoxDriver();
//        driver.get("https://www.facebook.com/");
//
//        driver.quit();
//    }

    @Test
    public void TC_02_Chrome() {
        System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.facebook.com/");


        driver.quit();

    }

    @Test
    public void TC_03_Edge() {
        // /Users/mastery/Desktop/AutomationTest/Java/edgedriver_mac64/msedgedriver
        try{
            System.setProperty("webdriver.edge.driver", projectPath + "/edgedriver_mac64/msedgedriver");
            driver = new EdgeDriver();
            driver.get("https://www.facebook.com/");
            driver.quit();
        }catch (Exception e){
            System.out.println("" + e);
        }


    }
}
