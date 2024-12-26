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

    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());
        try {
            Object testInstance = result.getInstance();
            page = (Page) testInstance.getClass().getField("page").get(testInstance);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (page != null) {
            try {
                String screenshotPath = getScreenShotPath(result.getMethod().getMethodName(), page);
                test.addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }

}