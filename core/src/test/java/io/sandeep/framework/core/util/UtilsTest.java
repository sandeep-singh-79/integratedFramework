package io.sandeep.framework.core.util;

import io.sandeep.framework.core.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertTrue;

public class UtilsTest {
    @Test
    public void testGetDate () {
        assertThat(Utils.getDate(), is(now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));
    }

    @Test
    public void testGetTimeStamp () {
        assertThat(Utils.getTimeStamp(), is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyyHH_mm_ss"))));
    }

    @Test
    public void testGetTimeStamp_when_format_is_supplied () {
        String format = "dd/mm/yyyy HH:mm:ss";
        assertThat(Utils.getTimeStamp(format), is(LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))));
    }

    @Test(enabled = false)
    public void testTake_screenshot_when_screenshotName_is_supplied () {
        WebDriver driver = WebDriverFactory.getInstance().getDriver("local");
        File screenshot = Utils.take_screenshot(driver, "test_screenshot");
        assertTrue(screenshot.exists());
        assertTrue(screenshot.isFile());

        if (driver != null) driver.quit();
    }
}