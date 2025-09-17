package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;

@Listeners(listeners.TestListener.class)
public class BrowserStackPermissionTest {

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

        // ✅ Store driver in context so listener can access it
        context.setAttribute("driver", driver);
    }

    @Test
    public void testHandlePermissionsAndPopups() {
        // ✅ Handle runtime Android permissions (Allow / Deny pop-ups)
        try {
            WebElement allowBtn = driver.findElement(By.id("com.android.permissioncontroller:id/permission_allow_button"));
            if (allowBtn.isDisplayed()) {
                allowBtn.click();
                System.out.println("✅ Allowed permission");
            }
        } catch (Exception e) {
            System.out.println("ℹ️ No runtime permission pop-up appeared.");
        }

        // ✅ Handle App-specific popups (example: skip tutorial, update prompt)
        try {
            WebElement skipTutorial = driver.findElement(By.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button"));
            if (skipTutorial.isDisplayed()) {
                skipTutorial.click();
                System.out.println("✅ Skipped app tutorial popup");
            }
        } catch (Exception e) {
            System.out.println("ℹ️ No app tutorial popup appeared.");
        }

        // ✅ Handle another possible popup (example: "OK" or "Later" in update prompt)
        try {
            WebElement okBtn = driver.findElement(By.xpath("//android.widget.Button[@text='OK']"));
            okBtn.click();
            System.out.println("✅ Handled OK popup");
        } catch (Exception e) {
            System.out.println("ℹ️ No OK popup appeared.");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
