package tests;

import baseTest.BaseTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import testutils.AdditionalDescriptions;

import java.util.List;

public class HomePageTest extends BaseTest {
    HomePage homePage;

    @BeforeMethod(dependsOnMethods = "initialize_Browser_With_Session")
    public void initializeHomePage() {
        getPage().navigate(prop.getProperty("homePageUrl"),
                new Page.NavigateOptions().setTimeout(100000));
        getPage().waitForLoadState(LoadState.DOMCONTENTLOADED);
        homePage = new HomePage(getPage());
    }

    @Test
    @AdditionalDescriptions({"WA-6341", "WA-0001"})
    public void verify_HomePage_Title_Page() {
        String pageTitle = getPage().title();
        Assert.assertEquals(pageTitle, "TruVideo - Home", "Home page title not matched");
    }

    @Test(description = "WA-6341")
    public void verify_User_Is_Able_To_Navigate_To_RepairOrder_Page() {
        homePage.click_RepairOrder_Button();
        String pageTitle = getPage().title();
        Assert.assertEquals(pageTitle, "TruVideo - Repair Orders", "Repair Order title not matched");
    }

    @Test(description = "WA-6341")
    public void verify_User_Is_Able_To_Navigate_To_Prospect_Page() {
        homePage.click_Prospect_Button();
        String pageTitle = getPage().title();
        Assert.assertEquals(pageTitle, "TruVideo - Prospects", "Prospect title not matched");
    }

    @Test(description = "WA-6341")
    public void verify_User_Is_Able_To_See_DealerCodes_Of_LoginDealer() {
        homePage.click_DealerCode_Button();
        boolean isDealerCodeDisplayed = false;
        if (homePage.getListOfDealer().contains("SERVICE CODE: 231146") && homePage.getListOfDealer().contains("SALES CODE: 324062")) {
            isDealerCodeDisplayed = true;
        }
        Assert.assertTrue(isDealerCodeDisplayed, "Dealer Code is not displayed");
    }

}
