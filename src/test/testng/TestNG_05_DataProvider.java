package testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestNG_05_DataProvider {

    @Test(dataProvider = "LoginDataProvider", dataProviderClass = TestNG_05_DataProvider.class) //class case dataProvider in diff class
    public void loginTest(String email, String pwd) {

        System.out.println(email + "    " + pwd);
    }

    @DataProvider(name = "LoginDataProvider")
    public Object[][] getData() {
        Object[][] data = {{"tesvtel56@yopmail.com", "123456aA@@"}, {"tesvtel57@yopmail.com", "123456aA@@"}, {"tesvtel58@yopmail.com", "123456aA@@"}};
        return data;
    }
}
