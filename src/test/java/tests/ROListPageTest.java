package tests;

import baseTest.BaseTest;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ROListPage;

public class ROListPageTest extends BaseTest {
    ROListPage roListPage;

    @BeforeMethod
    public void initializeROListPage() {
        getPage().navigate(prop.getProperty("roListPageUrl"),
                new Page.NavigateOptions().setTimeout(100000));
        roListPage = new ROListPage(getPage());
    }

    @Test
    public void verify_ROListPage_Title_Page() {
        String pageTitle = getPage().title();
        Assert.assertEquals(pageTitle, "TruVideo - Repair Orders", "RO List page title not matched");
    }

    @Test
    public void verify_Add_New_RepairOrder_Button_Working() {
        roListPage.clickAddRepairOrderButton();
        String addNewOrder_Header = roListPage.getHeading_AddNewOrder_Page();
        Assert.assertEquals(addNewOrder_Header, "Add Repair Order", "Add New Order page header not matched");

    }

    @Test
    public void verify_Close_RepairOrder_Button_Working() {
        roListPage.clickCloseRepairOrderButton();
        String warnMessage = roListPage.getWarningMessage();
        boolean flag = false;
        if (warnMessage.contains("Please select repair orders to be closed.")) {
            flag = true;
        }
        Assert.assertTrue(false, "Warning message not matched");
    }

}
