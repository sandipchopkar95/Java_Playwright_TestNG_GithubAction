package testutils;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;

public class Listeners extends TestUtils implements ITestListener {
    Page page;
    ExtentTest test;
    ExtentReports extent = TestUtils.getReporterObject();

    public void onTestSuccess(ITestResult result) {
        if (page != null) {
            try {
                // Capture a screenshot on failure
                String screenshotPath = getScreenShotPath(result.getMethod().getMethodName(), page);
                test.addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Initialize the ExtentTest for this test
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Ensure the test is initialized
        if (test == null) {
            System.err.println("ExtentTest instance is null in onTestFailure. Ensure onTestStart initializes it.");
            return;
        }

        // Log the failure in the report
        test.fail(result.getThrowable());
        try {
            // Retrieve the Page object from the test instance
            Object testInstance = result.getInstance();
            page = (Page) testInstance.getClass().getField("page").get(testInstance);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (page != null) {
            try {
                // Capture a screenshot on failure
                String screenshotPath = getScreenShotPath(result.getMethod().getMethodName(), page);
                test.addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (test != null) {
            test.skip(result.getThrowable());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optional: Add logic here if needed
    }

    @Override
    public void onStart(ITestContext context) {
        // Optional: Add logic here if needed
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the report to write it to the file
        extent.flush();
    }
}
