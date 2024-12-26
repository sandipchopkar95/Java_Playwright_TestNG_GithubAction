package testutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Page;
import io.appium.java_client.AppiumDriver;

public class TestUtils {
    static ExtentReports extent;

    public static ExtentReports getReporterObject() {
        String path = System.getProperty("user.dir") + "/Reports";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("RC Truvideo Web App");
        reporter.config().setDocumentTitle("Web Automation Test Report");
        reporter.config().setTheme(Theme.STANDARD);
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("5 Exceptions", "RC Truvideo");
        extent.setSystemInfo("Browser", "chrome");
        return extent;
    }

//    public String getScreenShotPath(String testCaseName, Page page) throws IOException {
//        String destinationField = "/Reports/Screenshots/" + testCaseName + ".png";
//        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(destinationField)));
//        return destinationField;
//    }

    public static void takeScreenshot(Page page, String methodName) {
        try {
            // Define the screenshot file path
            Path screenshotPath = Paths.get("Reports/screenshots", methodName + ".png");
            // Create parent directories if they do not exist
            screenshotPath.toFile().getParentFile().mkdirs();
            // Take a screenshot and save it to the specified path
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}