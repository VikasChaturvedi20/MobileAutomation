package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.URL;

public class BaseTest {
    protected AndroidDriver driver;

    @BeforeClass
    public void setup() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("emulator-5554") // run `adb devices` to confirm
                .setAutomationName("UiAutomator2")
                .setAppPackage("com.miui.calculator")
                .setAppActivity("com.miui.calculator.cal.CalculatorActivity")
                // 🔧 Stability options
                .amend("uiautomator2ServerLaunchTimeout", 60000) // 60 sec
                .amend("adbExecTimeout", 60000)                  // 60 sec
                .amend("newCommandTimeout", 120)                 // 2 min idle timeout
                .amend("autoGrantPermissions", true);            // auto-accept app permissions

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                options
        );

        // ✅ Handle MIUI "Agree" popup if it appears
        try {
            Thread.sleep(2000); // wait a bit for popup
            driver.findElement(By.id("android:id/button1")).click(); // "Agree" button
            System.out.println("👉 Permissions/EULA popup dismissed");
        } catch (NoSuchElementException e) {
            System.out.println("👉 No popup detected, continuing...");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
