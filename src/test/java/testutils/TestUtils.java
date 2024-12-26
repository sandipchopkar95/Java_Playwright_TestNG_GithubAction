package testutils;

import java.io.File;
import java.io.IOException;
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
//        String destinationField = System.getProperty("user.dir") + "/Reports/ScreenShots/" + testCaseName + ".png";
//        // Capture the screenshot and save it to the destination path
//        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(destinationField)));
//        return destinationField;
//    }

    public static String getScreenShotPath(String testName, Page page) throws IOException {
        String screenshotDirectory = "./Reports/Screenshots/";
        String screenshotPath = screenshotDirectory + testName + ".png";

        java.nio.file.Files.createDirectories(Paths.get(screenshotDirectory));
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        return screenshotPath;
    }

}