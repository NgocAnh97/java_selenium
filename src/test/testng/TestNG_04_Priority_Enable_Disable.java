package testng;

import org.testng.annotations.Test;

public class TestNG_04_Priority_Enable_Disable {

    @Test(priority = 2, enabled = false)
    public void TC_01(){
        System.out.println("This is test1...");
    }

    @Test(priority = 1)
    public void TC_02(){
        System.out.println("This is test2...");
    }
    @Test(priority = 3)
    public void TC_03(){
        System.out.println("This is test3...");
    }

    @Test(priority = 4)
    public void TC_04(){
        System.out.println("This is test4...");
    }
    @Test(priority = 5)
    public void TC_05(){
        System.out.println("This is test5...");
    }

    @Test(priority = 7)
    public void TC_06(){
        System.out.println("This is test6...");
    }
}
