package factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static factory.PlaywrightFactory.getBrowser;

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
            page.navigate("https://rc.truvideo.com/login");
            page.fill(".input-prepend input[name='j_username']", "dineshadvisor@gmail.com");
            page.fill(".input-prepend input[name='j_password']", "Sandip@1234");
            page.click("input[value='Log In']");
            // Wait for successful login
            page.waitForTimeout(3000);
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
