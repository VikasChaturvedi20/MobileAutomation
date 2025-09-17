package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String captureScreenshot(AppiumDriver driver, String testName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String destDir = System.getProperty("user.dir") + "/reports/screenshots/";
        Files.createDirectories(new File(destDir).toPath());

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = destDir + testName + "_" + timestamp + ".png";

        File destFile = new File(filePath);
        Files.copy(srcFile.toPath(), destFile.toPath());

        return destFile.getAbsolutePath(); // ✅ Absolute path for Extent Report
    }
}
