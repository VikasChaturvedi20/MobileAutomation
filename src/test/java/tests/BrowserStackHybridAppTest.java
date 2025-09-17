package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

@Listeners(listeners.TestListener.class)
public class BrowserStackHybridAppTest {

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
                // ✅ Using BrowserStack’s built-in hybrid sample app
                .setApp("bs://sample-hybrid-app");

        driver = new AndroidDriver(new URL(URL), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // ✅ store driver in context so listener can access it
        context.setAttribute("driver", driver);
    }

    @Test
    public void testHybridAppContextSwitching() throws InterruptedException {
        // ✅ Step 1: Work in Native Context
        System.out.println("👉 Current Context: " + driver.getContext()); // should be NATIVE_APP
        WebElement loginButton = driver.findElement(By.id("login")); // sample-hybrid-app login button
        loginButton.click();
        System.out.println("✅ Clicked login button in native view");

        // Wait for WebView to load
        Thread.sleep(5000);

        // ✅ Step 2: Get all available contexts
        Set<String> contexts = driver.getContextHandles();
        System.out.println("👉 Available Contexts: " + contexts);

        // ✅ Step 3: Switch to WebView context
        for (String contextName : contexts) {
            if (contextName.contains("WEBVIEW")) {
                driver.context(contextName);
                break;
            }
        }

        System.out.println("👉 Switched to Context: " + driver.getContext());

        // ✅ Step 4: Perform WebView actions
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.sendKeys("testuser@example.com");

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys("password123");

        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        submitBtn.click();

        System.out.println("✅ Submitted login form inside WebView");

        // ✅ Step 5: Switch back to Native context
        driver.context("NATIVE_APP");
        System.out.println("👉 Back to Context: " + driver.getContext());

        // Example: validate success message
        WebElement successMsg = driver.findElement(By.id("success"));
        assert successMsg.isDisplayed();
        System.out.println("✅ Login success message displayed in native view");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
