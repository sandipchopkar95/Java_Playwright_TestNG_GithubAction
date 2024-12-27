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
        getBrowserContext().tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
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
            attachScreenshotToReport(test, methodName, page);
            attachTrace(test, methodName);
            //attachVideo(methodName);
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
            attachScreenshotToReport(test, methodName, page);
            attachTrace(test, methodName);
            //attachVideo(methodName);
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
}
