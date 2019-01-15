package io.sandeep.framework.core.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.apache.commons.io.FileUtils.copyFile;

@Slf4j
public class Utils {
    public static String getDate () {
        return (LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
    }

    public static String getTimeStamp () {
        return (getTimeStamp("dd-MMM-yyyy HH_mm_ss").replaceAll(" ", ""));
    }

    public static String getTimeStamp (String dateFormat) {
        return (LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat)));
    }

    public static File take_screenshot (WebDriver driver, ITestResult testResult) throws NullPointerException {
        return take_screenshot(driver, testResult.getMethod().getMethodName());
    }

    public static File take_screenshot (WebDriver driver, final String method_name) {
        if (driver == null) throw new NullPointerException();

        String screenshot_dir_path = null;
        try {
            screenshot_dir_path = new File(String.format("%s/../Screenshots/%s", System.getProperty("user.dir"), getDate())).getCanonicalPath();
        } catch (IOException e) {
            log.error("error encountered while getting path of the screenshot directory!");
        }
        File screenshotFile = null;
        try {
            copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), create_screenshot_file(method_name,
                    createDirectory(screenshot_dir_path)), true);
        } catch (Exception ioe) {
            log.error(screenshot_dir_path);
            log.error("Encountered issue while creating screenshot for method/scenario {}", method_name);
            log.error("This was caused by {}", ioe.getMessage());
            log.error(Arrays.toString(ioe.getStackTrace()));
        }

        return screenshotFile;
    }

    private static File create_screenshot_file (final String method_name, final File screenshot_dir) throws IOException {
        return new File(String.format("%s/%s_%s.png", screenshot_dir.getCanonicalPath(), method_name, Utils.getTimeStamp("dd-MMM-yyyy_HH_mm_ss")));
    }

    private static File createDirectory (String directoryPath) {
        File screenshotDir = new File(directoryPath);
        if (!screenshotDir.exists()) screenshotDir.mkdirs();
        return screenshotDir;
    }
}
