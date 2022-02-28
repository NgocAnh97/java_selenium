package testng;

import org.testng.annotations.*;

public class TestNG_01_Annotations {

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("This will execute before every Method...");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("This will execute before every Class...");
    }
    @BeforeSuite
    public void beforeSuite(){
        System.out.println("This will execute before every Suite...");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("This will execute after every Method...");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("This will execute after every Class...");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("This will execute after every Suite...");
    }
    @Test
    public void TC_01(){
        System.out.println("This is test1...");
    }

    @Test
    public void TC_02(){
        System.out.println("This is test2...");
    }
}
