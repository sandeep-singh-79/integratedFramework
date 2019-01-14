package io.sandeep.framework.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class WebDriverFactoryTest {
    WebDriverFactory driverFactory;

    @BeforeMethod
    public void setUp () {
        driverFactory = WebDriverFactory.getInstance();
    }

    @AfterMethod
    public void tearDown () {
        driverFactory = null;
    }

    @Test
    public void testGetInstance () {
        assertTrue(driverFactory instanceof WebDriverFactory);
    }

    @Test
    public void testGetLocalChromeDriver () {
        WebDriver local = driverFactory.getDriver("local");
        assertTrue(local instanceof ChromeDriver);
    }

    // TODO: need to provide config params for RemoteDriver instance
    @Test (enabled = false)
    public void testGetRemoteChromeDriver () {
        WebDriver remote = driverFactory.getDriver("remote");
        assertTrue(remote instanceof RemoteDriver);
    }

    @Test (enabled = false)
    public void testGetLocalFirefoxDriver () {
        System.setProperty("browser", "firefox");
        WebDriver local = driverFactory.getDriver("local");
        assertTrue(local instanceof FirefoxDriver);
    }

    @Test
    public void testCloseDriver () {
        WebDriver local = driverFactory.getDriver("local");
        local.quit(); local = null;
        assertNull(local);
    }

    @Test
    public void test_local_driver_created_if_unsupported_driver_type_is_specified () {
        WebDriver driver = driverFactory.getDriver("mobile");
        assertTrue(driver instanceof ChromeDriver);
        driver.quit();
    }

    @Test(expectedExceptions = CloneNotSupportedException.class)
    public void test_cannot_clone() throws CloneNotSupportedException {
        driverFactory.clone();
    }
}