package listeners;

import com.aventstack.extentreports.*;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.ReportManager;
import utils.ScreenshotUtils;

import io.appium.java_client.AppiumDriver;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ReportManager.getInstance();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName())
                .assignAuthor("Vikas")
                .assignCategory("BrowserStack Tests");
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            AppiumDriver driver = (AppiumDriver) result.getTestContext().getAttribute("driver");
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
            extentTest.get().pass("✅ Test Passed",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            extentTest.get().pass("✅ Test Passed (screenshot not available)");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            AppiumDriver driver = (AppiumDriver) result.getTestContext().getAttribute("driver");
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
            extentTest.get().fail("❌ Test Failed: " + result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            extentTest.get().fail("❌ Test Failed (screenshot not available)");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("⚠️ Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
