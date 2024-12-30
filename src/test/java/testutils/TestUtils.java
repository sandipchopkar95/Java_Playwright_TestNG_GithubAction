package testutils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Tracing;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;

import static factory.PlaywrightFactory.getBrowserContext;
import static testutils.MyScreenRecorder.stopRecording;

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

    public static void takeScreenshot(Page page, String methodName) {
        try {
            Path screenshotPath = Paths.get("./Reports/screenshots", methodName + ".png");
            screenshotPath.toFile().getParentFile().mkdirs();
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attachScreenshotToReport(ExtentTest test, String methodName, Page page) {
        takeScreenshot(page, methodName);
        String getScreenshotPath = "screenshots/" + methodName + ".png";
        test.addScreenCaptureFromPath(getScreenshotPath, methodName);
    }

    public void attachTrace(ExtentTest test,String methodName) {
        createTrace(methodName);
        String getTracePath = "traces/" + methodName + "-trace.zip";
        test.info("Trace: <a href='" + getTracePath + "' target='_blank'>Download Trace</a>");
    }

    public void createTrace(String testName) {
        String traceDir = "./Reports/traces";
        String traceFileName = testName + "-trace.zip";
        try {
            Path dir = Paths.get(traceDir);
            java.nio.file.Files.createDirectories(dir);
            Path tracePath = dir.resolve(traceFileName);
            getBrowserContext().tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        } catch (IOException e) {
            System.out.println("Failed to save trace for test: " + e.getMessage());
        }
    }

    public void attachVideo(ExtentTest test, String methodName) throws Exception {
        stopRecording();
        String videoPath = "videos/" + methodName + ".avi";
        File videoFile = new File(videoPath);
        if (videoFile.exists()) {
            test.info("Video: <a href='" + videoPath + "' target='_blank'>Download Video</a>");
        } else {
            test.info("Video not available for this test.");
        }
        test.addVideoFromPath(videoPath);
    }

}