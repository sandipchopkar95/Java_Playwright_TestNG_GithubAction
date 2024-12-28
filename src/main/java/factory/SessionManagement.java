package factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import pages.LoginPage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static factory.PlaywrightFactory.getBrowser;
import static factory.PlaywrightFactory.prop;

public class SessionManagement {

    private static final String SESSION_FILE = "./UserSession/session.json";

    public static BrowserContext loginAndStoreSession_UsingValidCredentials() {
        Path sessionPath = Paths.get(SESSION_FILE);
        BrowserContext context;
        if (Files.exists(sessionPath)) {
            System.out.println("Session file found. Using existing session.");
            context = getBrowser().newContext(new Browser.NewContextOptions()
                    .setStorageStatePath(sessionPath));
        } else {
            System.out.println("Session file not found. Logging in to create a new session.");
            context = getBrowser().newContext(); // Create a new context
            Page page = context.newPage();
            page.navigate(prop.getProperty("loginPageUrl"));
            LoginPage loginPage =new LoginPage(page);
            loginPage.loginWithValidCredential(prop.getProperty("username"), prop.getProperty("password"));
            // Save session to a file
            context.storageState(new BrowserContext.StorageStateOptions().setPath(sessionPath));
            System.out.println("Session state saved.");
            page.close();
        }
        return context; // Return the context in both cases
    }

    public static void clearSessionFile() {
        Path sessionPath = Paths.get(SESSION_FILE);
        try {
            if (Files.exists(sessionPath)) {
                Files.delete(sessionPath);
                System.out.println("Session file cleared.");
            } else {
                System.out.println("No session file found to delete.");
            }
        } catch (Exception e) {
            System.err.println("Failed to delete session file: " + e.getMessage());
        }
    }
}
