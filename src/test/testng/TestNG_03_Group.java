package testng;

import org.testng.annotations.Test;

public class TestNG_03_Group {
    @Test(groups = "type1")
    public void TC_01(){
        System.out.println("This is test1...");
    }

    @Test(groups = "type1")
    public void TC_02(){
        System.out.println("This is test2...");
    }
    @Test(groups = "type2")
    public void TC_03(){
        System.out.println("This is test3...");
    }

    @Test(groups = "type2")
    public void TC_04(){
        System.out.println("This is test4...");
    }
    @Test(groups = {"type1","type2"})
    public void TC_05(){
        System.out.println("This is test5...");
    }

    @Test(groups = {"type1","type2"})
    public void TC_06(){
        System.out.println("This is test6...");
    }
}
