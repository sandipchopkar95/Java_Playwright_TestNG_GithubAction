package pages;

import com.microsoft.playwright.Page;

public class HomePage {
    private Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    private final String header_label = ".hero__title";

    public String get_Header_Label() {
        return page.textContent(header_label);
    }

}
