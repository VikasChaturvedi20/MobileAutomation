package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CalculatorPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public CalculatorPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators for MIUI Calculator (Xiaomi)
    private final By digit5 = By.id("com.miui.calculator:id/btn_5");
    private final By digit9 = By.id("com.miui.calculator:id/btn_9");
    private final By opAdd = By.id("com.miui.calculator:id/btn_plus_s");
    private final By equal = By.id("com.miui.calculator:id/btn_equal_s");
    private final By result = By.id("com.miui.calculator:id/result");

    // Utility to safely find elements
    private WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Actions
    public void press5() {
        find(digit5).click();
    }

    public void press9() {
        find(digit9).click();
    }

    public void pressAdd() {
        find(opAdd).click();
    }

    public void pressEqual() {
        find(equal).click();
    }

    public String getResult() {
        return find(result).getText();
    }
}
