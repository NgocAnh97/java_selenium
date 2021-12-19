package Exercises;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

// Link docs: https://docs.google.com/document/d/1G0QsiVDI5KfhNGKxE6a03bhn6KpvgDh69BTx2ttCO94/edit#
public class Topic06_Browser_Exercise {
    WebDriver driver;
    String urlBrowser ="http://live.techpanda.org/";
    String urlElement ="https://automationfc.github.io/basic-form/index.html";
    String urlMailChimp ="https://login.mailchimp.com/signup/";
    String projectPath = System.getProperty("user.dir") + "/driver";

    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.chrome.driver", projectPath + "/chromedriver");
        driver = new ChromeDriver();
        // Wait element to ready
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //@Test
    public void TC_01_VerifyURL() {
        driver.get(urlBrowser);
        driver.findElement(By.cssSelector(".footer a[title = 'My Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.cssSelector(".buttons-set a")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/create/");
    }

    //@Test
    public void TC_02_VerifyTitle() {
        driver.get(urlBrowser);
        driver.findElement(By.cssSelector(".footer a[title = 'My Account']")).click();
        Assert.assertEquals(driver.getTitle(),"Customer Login");

        driver.findElement(By.cssSelector(".buttons-set a")).click();
        Assert.assertEquals(driver.getTitle(),"Create New Customer Account");
    }

    //@Test
    public void TC_03_Navigate() {
        driver.get(urlBrowser);
        driver.findElement(By.cssSelector(".footer a[title = 'My Account']")).click();
        driver.findElement(By.cssSelector(".buttons-set a")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/create/");
        driver.navigate().back();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/login/");
        driver.navigate().forward();
        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
    }

    //@Test
    public void TC_04_GetSourceCode() {
        driver.get(urlBrowser);
        driver.findElement(By.cssSelector(".footer a[title = 'My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));
        driver.findElement(By.cssSelector(".buttons-set a")).click();
        Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
    }

    //@Test
    public void TC_05_IsDisplayed() {
        driver.get(urlElement);

        WebElement emailTextbox = driver.findElement(By.cssSelector("#mail"));
        if(emailTextbox.isDisplayed()){
            System.out.println("Email textbox is displayed");
            emailTextbox.sendKeys("Automation Testing");
        } else {
            System.out.println("Email textbox is not displayed");
        }

        WebElement ageUnder18Radio = driver.findElement(By.cssSelector("#under_18"));
        if(ageUnder18Radio.isDisplayed()){
            System.out.println("Age under 18 radio is displayed");
            ageUnder18Radio.click();
        } else {
            System.out.println("Age under 18 radio is not displayed");
        }

        WebElement educationTextArea = driver.findElement(By.cssSelector("#edu"));
        if(educationTextArea.isDisplayed()){
            System.out.println("Education text area is displayed");
            educationTextArea.sendKeys("Automation Testing");
        } else {
            System.out.println("Education text area is not displayed");
        }

        WebElement nameH5 = driver.findElement(By.xpath("//div[@class='figure'][1]/div[@class='figcaption']/h5"));
        if(nameH5.isDisplayed()){
            System.out.println("Name user 5 is displayed");
            educationTextArea.sendKeys("Automation Testing");
        } else {
            System.out.println("Name user 5 is not displayed");
        }
    }

    //@Test
    public void TC_06_IsEnable() {
        driver.get(urlElement);

        // Step 02: Check elements is enabled
        System.out.println("//Step 02: Check elements is enabled:");
        WebElement emailTextbox = driver.findElement(By.cssSelector("#mail"));
        if(emailTextbox.isEnabled()){
            System.out.println("Email textbox is enabled");
        } else {
            System.out.println("Email textbox is disable");
        }

        WebElement ageUnder18Radio = driver.findElement(By.cssSelector("#under_18"));
        if(ageUnder18Radio.isEnabled()){
            System.out.println("Age under 18 radio is enabled");
        } else {
            System.out.println("Age under 18 radio is disable");
        }

        WebElement educationTextArea = driver.findElement(By.cssSelector("#edu"));
        if(educationTextArea.isEnabled()){
            System.out.println("Education text area is enabled");
        } else {
            System.out.println("Education text area is disable");
        }

        WebElement job1Dropdown = driver.findElement(By.cssSelector("#job1"));
        if(job1Dropdown.isEnabled()){
            System.out.println("Job 1 dropdown is enabled");
        } else {
            System.out.println("Job 1 dropdown is disable");
        }

        WebElement job2Dropdown = driver.findElement(By.cssSelector("#job2"));
        if(job2Dropdown.isEnabled()){
            System.out.println("Job 2 dropdown is enabled");
        } else {
            System.out.println("Job 2 dropdown is disable");
        }

        WebElement developerCheckbox = driver.findElement(By.cssSelector("#development"));
        if(developerCheckbox.isEnabled()){
            System.out.println("Developer checkbox is enabled");
        } else {
            System.out.println("Developer checkbox is disable");
        }

        WebElement slide1Slider = driver.findElement(By.cssSelector("#slider-1"));
        if(slide1Slider.isEnabled()){
            System.out.println("Slider 1 is enabled");
        } else {
            System.out.println("Slider 1 is disable");
        }

        // Step 03: Check elements is disable
        System.out.println("\n//Step 03: Check elements is disable:");
        WebElement passwordTextbox = driver.findElement(By.cssSelector("#password"));
        if(passwordTextbox.isEnabled()){
            System.out.println("Password checkbox is enabled");
        } else {
            System.out.println("Password checkbox is disable");
        }

        WebElement radioDisabledCheckbox = driver.findElement(By.cssSelector("#radio-disabled"));
        if(radioDisabledCheckbox.isEnabled()){
            System.out.println("Radio button disabled checkbox is enabled");
        } else {
            System.out.println("Radio button disabled checkbox is disable");
        }

        WebElement biographyTextArea = driver.findElement(By.cssSelector("#bio"));
        if(biographyTextArea.isEnabled()){
            System.out.println("Biography textarea is enabled");
        } else {
            System.out.println("Biography textarea is disable");
        }

        WebElement job3Dropdown = driver.findElement(By.cssSelector("#job3"));
        if(job3Dropdown.isEnabled()){
            System.out.println("Job 3 dropdown is enabled");
        } else {
            System.out.println("Job 3 dropdown is disable");
        }

        WebElement interestDisabledCheckbox = driver.findElement(By.cssSelector("#check-disbaled"));
        if(interestDisabledCheckbox.isEnabled()){
            System.out.println("Interests Checkbox disabled is enabled");
        } else {
            System.out.println("Interests Checkbox disabled is disable");
        }

        WebElement slide2Slider = driver.findElement(By.cssSelector("#slider-2"));
        if(slide2Slider.isEnabled()){
            System.out.println("Slide 2 is enabled");
        } else {
            System.out.println("Slide 2 is disable");
        }
    }

    //@Test
    public void TC_07_IsSelected() {
        driver.get(urlElement);

        // Check elements is selected
        System.out.println("// Kiem tra phan tu selected:");
        WebElement ageUnder18Radio = driver.findElement(By.cssSelector("#under_18"));
        ageUnder18Radio.click();
        if (ageUnder18Radio.isSelected()) {
            System.out.println("Email textbox is selected");
        } else {
            System.out.println("Email textbox is de-selected");
        }

        WebElement languagesJavaCheckbox = driver.findElement(By.cssSelector("#java"));
        languagesJavaCheckbox.click();
        if (languagesJavaCheckbox.isSelected()) {
            System.out.println("Language Java checkbox is selected");
        } else {
            System.out.println("Language Java checkbox is de-selected");
        }

        languagesJavaCheckbox.click();
        if (languagesJavaCheckbox.isSelected()) {
            System.out.println("Language Java checkbox is selected");
        } else {
            System.out.println("Language Java checkbox is de-selected");
        }
    }

    @Test
    public void TC_08_RegisterMailChimp() {
        driver.get(urlMailChimp);

        System.out.println("// Register function at MailChimp:");
        driver.findElement(By.cssSelector("#email")).sendKeys("test@gmail.com");
        driver.findElement(By.cssSelector("#new_username")).sendKeys("test");

        WebElement passwordTextbox = driver.findElement(By.cssSelector("#new_password"));
        WebElement signUpBtn = driver.findElement(By.cssSelector("#create-account"));

        // 1 - Number
        passwordTextbox.sendKeys("123456");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed' and text()='One number']")).isDisplayed());
        Assert.assertFalse(signUpBtn.isEnabled());

        // 2 - Lowercase character
        passwordTextbox.clear();
        passwordTextbox.sendKeys("abcde");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed' and text()='One lowercase character']")).isDisplayed());
        Assert.assertFalse(signUpBtn.isEnabled());

        // 3 - Uppercase character
        passwordTextbox.clear();
        passwordTextbox.sendKeys("ABCDE");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed' and text()='One uppercase character']")).isDisplayed());
        Assert.assertFalse(signUpBtn.isEnabled());

        // 4 - Special character
        passwordTextbox.clear();
        passwordTextbox.sendKeys("!@#");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed' and text()='One special character']")).isDisplayed());
        Assert.assertFalse(signUpBtn.isEnabled());

        // 5 - More than 8 characters
        passwordTextbox.clear();
        passwordTextbox.sendKeys("123dfghrt");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed' and text()='8 characters minimum']")).isDisplayed());
        Assert.assertFalse(signUpBtn.isEnabled());

        // 6 - Combine
        passwordTextbox.clear();
        passwordTextbox.sendKeys("123456aA@@");
        Assert.assertTrue(driver.findElement(By.xpath("//h4[text()='Your password is secure and you're all set!']")).isDisplayed());
        Assert.assertTrue(signUpBtn.isEnabled());

        signUpBtn.click();
        sleepInsecond(5);
    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }

    public void sleepInsecond(long second){
        try{
            Thread.sleep(second*1000);
        } catch  (InterruptedException e){
            e.printStackTrace();
        }
    }
}
