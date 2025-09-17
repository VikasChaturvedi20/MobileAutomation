/*package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method; // ✅ correct import
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.UUID;

public class BrowserStackSampleTest {

	public static final String USERNAME = "vikaschaturvedi_uVl4BU";
	public static final String ACCESS_KEY = "snxaCAeAB7JGR1o1wBnP";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.browserstack.com/wd/hub";

	private AndroidDriver driver;
	private static ExtentReports extent;
	private static ExtentTest test;

	@BeforeSuite
	public void setupReport() {
		ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
		spark.config().setDocumentTitle("BrowserStack Automation Report");
		spark.config().setReportName("Sample App Tests");

		extent = new ExtentReports();
		extent.attachReporter(spark);
	}

	@BeforeMethod
	public void setup(Method method) throws Exception {
		UiAutomator2Options options = new UiAutomator2Options().setPlatformName("Android")
				.setDeviceName("Google Pixel 7").setPlatformVersion("13.0").setAutomationName("UiAutomator2")
				.setApp("bs://sample.app");

		driver = new AndroidDriver(new URL(URL), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		test = extent.createTest(method.getName()).assignAuthor("Vikas").assignDevice("BrowserStack Pixel 7");
	}

	@Test
	public void testWikipediaSearchOnBrowserStack() {
		test.info("✅ App Launched on BrowserStack!");

		try {
			WebElement skipButton = driver.findElement(By.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button"));
			skipButton.click();
			test.pass("Skipped onboarding screen");
		} catch (Exception e) {
			test.info("Skip button not found, continuing.");
		}

		driver.findElement(By.id("org.wikipedia.alpha:id/search_container")).click();
		WebElement searchBox = driver.findElement(By.id("org.wikipedia.alpha:id/search_src_text"));
		searchBox.sendKeys("Appium");
		test.info("Entered 'Appium' in search box");

		WebElement first = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Appium')]"));
		Assert.assertTrue(first.isDisplayed(), "No 'Appium' result found");

		test.pass("Search result for 'Appium' found ✅");
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (driver != null) {
			String screenshotPath = takeScreenshot(result.getName());

			if (result.getStatus() == ITestResult.FAILURE) {
				test.fail("❌ Test Failed: " + result.getThrowable(),
						MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				test.pass("✅ Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			} else {
				test.skip("⚠️ Test Skipped", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			}

			driver.quit();
		}
	}

	@AfterSuite
	public void flushReport() {
		extent.flush();
	}

	// ✅ Screenshot utility
	private String takeScreenshot(String testName) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String destDir = "reports/screenshots/";
		Files.createDirectories(Paths.get(destDir));

		String filePath = destDir + testName + "_" + UUID.randomUUID() + ".png";
		File destFile = new File(filePath);
		Files.copy(srcFile.toPath(), destFile.toPath());

		return "screenshots/" + destFile.getName(); // relative path for Extent HTML
	}
}
*/

package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;

import org.testng.annotations.Listeners;
import listeners.TestListener;

@Listeners(TestListener.class)
public class BrowserStackSampleTest {

    public static final String USERNAME = "vikaschaturvedi_uVl4BU";
    public static final String ACCESS_KEY = "snxaCAeAB7JGR1o1wBnP";
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.browserstack.com/wd/hub";

    private AndroidDriver driver;

    @BeforeMethod
    public void setup(org.testng.ITestContext context) throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("Google Pixel 7")
                .setPlatformVersion("13.0")
                .setAutomationName("UiAutomator2")
                .setApp("bs://sample.app");

        driver = new AndroidDriver(new URL(URL), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // make driver available to listener
        context.setAttribute("driver", driver);
    }

    @Test
    public void testWikipediaSearchOnBrowserStack() {
        try {
            WebElement skipButton = driver.findElement(By.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button"));
            skipButton.click();
        } catch (Exception e) {
            // skip button may not always appear
        }

        driver.findElement(By.id("org.wikipedia.alpha:id/search_container")).click();
        WebElement searchBox = driver.findElement(By.id("org.wikipedia.alpha:id/search_src_text"));
        searchBox.sendKeys("Appium");

        WebElement first = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Appium')]"));
        Assert.assertTrue(first.isDisplayed(), "No 'Appium' result found");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
