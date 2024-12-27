package baseTest;

import java.util.Properties;

import factory.PlaywrightFactory;
import org.testng.annotations.*;
import com.microsoft.playwright.Page;
import pages.HomePage;

public class BaseTest {
    protected PlaywrightFactory pf;
    public Page page;
    protected Properties prop;
    public HomePage homePage;

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

        homePage = new HomePage(page);
    }

    @AfterMethod
    public void tearDown() {
        page.context().browser().close();
    }

}
