package factory;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screensize.getWidth();
    int height = (int) screensize.getHeight();


    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    //public Page initBrowser(Properties prop)
    public Page initBrowser(String browserName, boolean headless) {
        //String browserName = prop.getProperty("browser").trim();     //Comment this line if we want to start by parameter
        System.out.println("Browser name is : " + browserName);
        tlPlaywright.set(Playwright.create());
        ArrayList<String> argument = new ArrayList<>();
        argument.add("--start-maximized");

        switch (browserName.toLowerCase()) {
            case "chromium":
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "firefox":
                tlBrowser.set(getPlaywright().firefox().launch(new LaunchOptions().setChannel("firefox").setHeadless(false).setArgs(argument)));
                break;
            case "safari":
                tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "chrome":
                tlBrowser.set(
                        getPlaywright().chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(headless).setArgs(argument)));
                break;
            case "edge":
                tlBrowser.set(
                        getPlaywright().chromium().launch(new LaunchOptions().setChannel("msedge").setHeadless(false).setArgs(argument)));
                break;

            default:
                System.out.println("Please pass the right browser name here......");
                break;
        }
        if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
            tlBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(null)));
            tlPage.set(getBrowserContext().newPage());
            getPage().navigate("https://playwright.dev/java/");
            //getPage().setViewportSize(width, height);
            return getPage();
        } else {
            tlBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(width, height)));
            tlPage.set(getBrowserContext().newPage());
            getPage().navigate("https://playwright.dev/java/");
            getPage().setViewportSize(width, height);
            return getPage();
        }
    }
    public static Properties prop;

    public Properties init_prop() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException f) {
            f.printStackTrace();
        }
        return prop;
    }

    public static String takeScreenshot(Page page) {
        String path = System.getProperty("user.dir" + "/screenshots/" + System.currentTimeMillis() + ".png");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        return path;
    }
}
