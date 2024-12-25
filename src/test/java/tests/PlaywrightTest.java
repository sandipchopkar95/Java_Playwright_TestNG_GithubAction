package tests;

import baseTest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaywrightTest extends BaseTest {

    @Test(priority = 1)
    public void verify_Home_Page_Header(){
        String actualHeader=homePage.get_Header_Label();
        Assert.assertEquals(actualHeader,"Playwright enables reliable end-to-end testing for modern web apps.");
    }

    @Test(priority = 2)
    public void verify_Home_Page_Header_Failed(){
        String actualHeader=homePage.get_Header_Label();
        Assert.assertEquals(actualHeader,"Failed");
    }

    @Test(priority = 3)
    public void verify_Home_Page_Header_Failed2(){
        String actualHeader=homePage.get_Header_Label();
        Assert.assertEquals(actualHeader,"Failed");
    }

    @Test(priority = 4)
    public void verify_Home_Page_Header_Failed3(){
        String actualHeader=homePage.get_Header_Label();
        Assert.assertEquals(actualHeader,"Failed");
    }
}
