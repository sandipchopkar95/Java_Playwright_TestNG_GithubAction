package factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import com.microsoft.playwright.*;


public class PlaywrightFactory {

    String videoDir = "./Reports/videos";

    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();

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

    public Page initBrowser(String browserName, boolean headless) {
        System.out.println("Browser name is: " + browserName);
        tlPlaywright.set(Playwright.create());

        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--start-maximized");

        switch (browserName.toLowerCase()) {
            case "chrome":
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "firefox":
                tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless).setArgs(arguments)));
                break;
            case "webkit":
                tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name provided: " + browserName);
        }
        tlBrowserContext.set(getBrowser().newContext());
        tlPage.set(getBrowserContext().newPage());
        getPage().navigate("https://playwright.dev/java/");
        return getPage();
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
}
