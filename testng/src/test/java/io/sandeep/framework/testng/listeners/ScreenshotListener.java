package io.sandeep.framework.testng.listeners;

import io.sandeep.framework.core.config.FrameworkConfig;
import io.sandeep.framework.core.driver.WebDriverFactory;
import io.sandeep.framework.core.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.Arrays;

@Slf4j
public class ScreenshotListener extends TestListenerAdapter {
    @Override
    public void onTestFailure (ITestResult iTestResult) {
        super.onTestFailure(iTestResult);
        take_screenshot(iTestResult);
    }

    @Override
    public void onConfigurationFailure (ITestResult iTestResult) {
        super.onConfigurationFailure(iTestResult);
        take_screenshot(iTestResult);
    }

    private void take_screenshot (ITestResult iTestResult) {
        WebDriver driver = (WebDriver) iTestResult.getTestContext().getAttribute("driver");
        try {
            Utils.take_screenshot(driver, iTestResult);
        } catch (NullPointerException e) {
            log.error("encountered NPE while taking a screenshot!!");
            log.error("{}", e.getCause());
            log.error("{}", Arrays.toString(e.getStackTrace()));

            driver = WebDriverFactory.getInstance().getDriver(System.getProperty("driverType",
                    FrameworkConfig.getInstance().getConfigProperties().getProperty("DRIVERTYPE")));
            Utils.take_screenshot(driver, iTestResult);
        }
    }
}
