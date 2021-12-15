package topic1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Topic0405_Exercise {

    WebDriver driver;
    String projectPath = System.getProperty("user.dir");

    @Test
    public void TC_02_Chrome() {
        System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
       // driver.findE

        driver.quit();

    }
}
