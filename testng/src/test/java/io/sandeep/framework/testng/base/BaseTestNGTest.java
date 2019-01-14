package io.sandeep.framework.testng.base;

import io.sandeep.framework.core.config.FrameworkConfig;
import io.sandeep.framework.core.driver.WebDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

@Slf4j
public abstract class BaseTestNGTest {
    private WebDriverFactory driverFactory;

    protected WebDriver driver;
    protected Properties config;

    @BeforeTest(alwaysRun = true)
    public void initTest (ITestContext testContext) {
        // create a WebDriver instance on the basis of the settings
        // provided in framework config properties file
        config = FrameworkConfig.getInstance().getConfigProperties();
        driverFactory = WebDriverFactory.getInstance();
        driver = driverFactory.getDriver(System.getProperty("driverType", config.getProperty("DRIVERTYPE")));

        driver.manage().window().maximize();

        testContext.setAttribute("driver", driver);
    }

    protected void loadApplication () {
        driver.get(config.getProperty("url_dev"));
    }

    @AfterTest(alwaysRun = true)
    public void teardownTest () {
        driverFactory.closeDriver();
    }
}
