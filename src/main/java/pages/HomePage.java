package pages;

import com.microsoft.playwright.Page;

import java.util.List;

public class HomePage {
    private Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    private final String tab_RepairOrder = ".nav-item a:has-text('Repair Orders')";
    private final String tab_Prospect = ".nav-item a:has-text('Prospects')";
    private final String button_DealerCode = ".btn-group .dropdown-toggle:has-text('Dealer Code')";
    private final String list_DealerCode = "#dealer-menu-list li.nav-header";


    public void click_RepairOrder_Button() {
        page.click(tab_RepairOrder);
        page.waitForTimeout(3000);
    }

    public void click_Prospect_Button() {
        page.click(tab_Prospect);
        page.waitForTimeout(3000);
    }

    public void click_DealerCode_Button() {
        page.click(button_DealerCode);
        page.waitForTimeout(3000);
    }

    public List<String> getListOfDealer() {
        return page.locator(list_DealerCode).allInnerTexts();
    }

}
