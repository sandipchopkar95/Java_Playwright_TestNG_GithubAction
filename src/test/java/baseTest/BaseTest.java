package baseTest;

import java.util.Properties;

import factory.PlaywrightFactory;
import org.testng.annotations.*;
import com.microsoft.playwright.Page;
import pages.HomePage;

import static factory.SessionManagement.clearSessionFile;

public class BaseTest {
    protected PlaywrightFactory pf;
    public Page page;
    protected Properties prop;

    @Parameters({"browser", "headless"})
    @BeforeSuite
    public void browserSetup_And_StoreSession(@Optional("chrome") String browser, @Optional("false") String headless) {
        pf = new PlaywrightFactory();
        prop = pf.init_prop(); // will call config file

        if (browser == null || browser.isEmpty()) {
            browser = prop.getProperty("browser", "chrome");
        }
        if (headless == null || headless.isEmpty()) {
            headless = prop.getProperty("headless", "false");
        }
        boolean headlessMode = Boolean.parseBoolean(headless);
        page = pf.initBrowser(browser, headlessMode);
        page.context().browser().close();
    }

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void playwrightSetup(@Optional("chrome") String browser, @Optional("false") String headless) {
        pf = new PlaywrightFactory();
        prop = pf.init_prop(); // will call config file

        if (browser == null || browser.isEmpty()) {
            browser = prop.getProperty("browser", "chrome");
        }
        if (headless == null || headless.isEmpty()) {
            headless = prop.getProperty("headless", "false");
        }
        boolean headlessMode = Boolean.parseBoolean(headless);
        page = pf.initBrowser(browser, headlessMode);
    }

    @AfterMethod
    public void tearDown() {
        page.close();
    }

    @AfterSuite
    public void clearSession() {
        clearSessionFile();
    }

}
