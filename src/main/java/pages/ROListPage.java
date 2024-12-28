package pages;

import com.microsoft.playwright.Page;

public class ROListPage {
    Page page;

    public ROListPage(Page page) {
        this.page = page;
    }

    private final String button_AddRepairOrder = "button#repair-order-add";
    private final String button_CloseRepairOrder = "button#repair-order-close";
    private final String header_AddNewOrder_Page="#add-order-page-title-text";
    private final String warnMsg_notification=".notifications.top-right";

    public void clickAddRepairOrderButton() {
        page.click(button_AddRepairOrder);
    }

    public void clickCloseRepairOrderButton() {
        page.click(button_CloseRepairOrder);
    }

    public String getHeading_AddNewOrder_Page(){
        return page.textContent(header_AddNewOrder_Page);
    }

    public String getWarningMessage(){
        return page.textContent(warnMsg_notification);
    }

}
