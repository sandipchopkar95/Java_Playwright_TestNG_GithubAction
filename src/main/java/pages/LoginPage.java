package pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    private final String textField_UserName = ".input-prepend input[name='j_username']";
    private final String textField_Password = ".input-prepend input[name='j_password']";
    private final String button_LogIn = "input[value='Log In']";

    public void loginWithValidCredential(String username,String password) {
        page.fill(textField_UserName, username);
        page.fill(textField_Password, password);
        page.click(button_LogIn);
        page.waitForTimeout(3000);
    }

}
