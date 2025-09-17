package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

@Listeners(listeners.TestListener.class)
public class BrowserStackGestureTest {

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

        // ✅ store driver in context so listener can access it
        context.setAttribute("driver", driver);
    }

    @Test
    public void testGestures() {
        // ✅ Scroll using screen dimensions
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();

        driver.executeScript("mobile: swipeGesture", new HashMap<String, Object>() {{
            put("left", 0);
            put("top", (int) (height * 0.3));   // start 30% from top
            put("width", width);
            put("height", (int) (height * 0.5)); // swipe area = middle 50%
            put("direction", "up");
            put("percent", 0.75);
        }});

        // ✅ Long press on element
        WebElement element = driver.findElement(By.id("org.wikipedia.alpha:id/search_container"));
        driver.executeScript("mobile: longClickGesture", new HashMap<String, Object>() {{
            put("elementId", ((org.openqa.selenium.remote.RemoteWebElement) element).getId());
            put("duration", 2000); // 2 seconds
        }});
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
