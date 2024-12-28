package testutils;

import com.microsoft.playwright.*;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import static factory.PlaywrightFactory.getBrowserContext;
import static factory.PlaywrightFactory.getPage;

public class Listeners extends TestUtils implements ITestListener {

    // ThreadLocal to store page, test objects, and trace file paths for each test thread
    private static ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> threadLocalTest = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocalTracePath = new ThreadLocal<>();

    ExtentReports extent = TestUtils.getReporterObject();

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test entry in ExtentReports for this thread
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        threadLocalTest.set(test);
        // Generate a unique trace file path for this test
        String tracePath = "./traces/" + result.getMethod().getMethodName() + ".zip";
        threadLocalTracePath.set(tracePath);
        // Start tracing for this test
        getBrowserContext().tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        Page page = getPage();
        threadLocalPage.set(page);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        handleTestCompletion(result, true);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        handleTestCompletion(result, false);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Handle test skipped scenarios if needed
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Handle tests that fail but are within success percentage
    }

    @Override
    public void onStart(ITestContext context) {
        // Start of the test suite or test
    }

    @Override
    public void onFinish(ITestContext context) {
        // Finish the test suite or test and flush the reports
        extent.flush();
    }

    // Handle test completion for success or failure
    private void handleTestCompletion(ITestResult result, boolean isSuccess) {
        String methodName = result.getMethod().getMethodName();
        try {
            Page page = threadLocalPage.get();
            ExtentTest test = threadLocalTest.get();
            if (page != null && test != null) {
                attachScreenshotToReport(test, methodName, page);
                attachTrace(test, methodName);
                if (!isSuccess) {
                    test.fail(result.getThrowable());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadLocalPage.remove();
            threadLocalTest.remove();
            threadLocalTracePath.remove();
        }
    }

}
