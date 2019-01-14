package io.sandeep.framework.core.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.io.FileUtils.copyFile;

@Slf4j
public class Utils {
    public static String getDate () {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date dateobj = new Date();
        return (df.format(dateobj));
    }

    public static String getTimeStamp () {
        return (getTimeStamp("dd-MMM-yyyy HH:mm:ss").replaceAll(" ", ""));
    }

    public static String getTimeStamp (String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Date dateobj = new Date();
        return (df.format(dateobj));
    }

    public static File take_screenshot (WebDriver driver, ITestResult testResult) throws NullPointerException {
        return take_screenshot(driver, testResult.getMethod().getMethodName());
    }

    public static File take_screenshot (WebDriver driver, final String method_name) {
        if (driver == null) return null;

        File screenshotFile = null;
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            screenshotFile = create_screenshot_file(method_name,
                    createDirectory(String.format("%s/Screenshots/%s", System.getProperty("user.dir"), getDate())));
            copyFile(screenshot.getScreenshotAs(OutputType.FILE), screenshotFile);
        } catch (Exception ioe) {
            log.error("Encountered issue while creating screenshot for method/scenario {}", method_name);
            log.error("This was caused by {}", ioe.getCause());
            log.error(String.valueOf(ioe.getStackTrace()));
        }

        return screenshotFile;
    }

    private static File create_screenshot_file (final String method_name, final File screenshot_dir) {
        return new File(String.format("%s/%s_%s.png", screenshot_dir.getPath(), method_name, Utils.getTimeStamp("dd-MM-yyyy_HH_mm_ss")));
    }

    private static File createDirectory (String directoryPath) {
        File screenshotDir = new File(directoryPath);
        if (!screenshotDir.exists()) screenshotDir.mkdirs();
        return screenshotDir;
    }
}
