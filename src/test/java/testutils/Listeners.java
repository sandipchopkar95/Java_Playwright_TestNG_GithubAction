package testutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.playwright.Tracing;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;

import static factory.PlaywrightFactory.getBrowserContext;

public class Listeners extends TestUtils implements ITestListener {
    Page page;
    ExtentTest test;
    ExtentReports extent = TestUtils.getReporterObject();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        try {
            Object testInstance = result.getInstance();
            page = (Page) testInstance.getClass().getField("page").get(testInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (page != null) {
            attachScreenshotToReport(methodName, page, test);
            attachTrace(methodName);
            attachVideo(methodName);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
        String methodName = result.getMethod().getMethodName();
        try {
            Object testInstance = result.getInstance();
            page = (Page) testInstance.getClass().getField("page").get(testInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (page != null) {
            attachScreenshotToReport(methodName, page, test);
            attachTrace(methodName);
            attachVideo(methodName);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private void attachScreenshotToReport(String methodName, Page page, ExtentTest test) {
        takeScreenshot(page, methodName);
        String getScreenshotPath = "screenshots/" + methodName + ".png";
        test.addScreenCaptureFromPath(getScreenshotPath, methodName);
    }

    private void attachTrace(String methodName) {
        createTrace(methodName);
        String getTracePath = "traces/" + methodName + "-trace.zip";
        test.info("Trace: <a href='" + getTracePath + "' target='_blank'>Download Trace</a>");
    }

    private void createTrace(String testName) {
        String traceDir = "./Reports/traces";
        String traceFileName = testName + "-trace.zip";
        try {
            Path dir = Paths.get(traceDir);
            java.nio.file.Files.createDirectories(dir); // Ensure directory exists
            Path tracePath = dir.resolve(traceFileName);
            getBrowserContext().tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        } catch (IOException e) {
            System.out.println("Failed to save trace for test: " + e.getMessage());
        }
    }

    private void attachVideo(String methodName) {
        String videoPath = "videos/" + methodName + "-video.mp4";
        File videoFile = new File(videoPath);
        if (videoFile.exists()) {
            test.info("Video: <a href='" + videoPath + "' target='_blank'>Download Video</a>");
            test.addVideoFromPath(videoPath, "Video for test: " + methodName);
        } else {
            test.warning("Video not available for this test.");
        }
    }

}
