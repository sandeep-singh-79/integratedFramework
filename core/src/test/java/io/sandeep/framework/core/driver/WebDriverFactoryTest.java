package io.sandeep.framework.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class WebDriverFactoryTest {
    private WebDriverFactory driverFactory;

    @BeforeMethod
    public void setUp () {
        driverFactory = WebDriverFactory.getInstance();
    }

    @Test
    public void testGetInstance () {
        assertTrue(driverFactory != null);
    }

    @Test
    public void testGetLocalChromeDriver () {
        WebDriver local = driverFactory.getDriver("local");
        assertTrue(local instanceof ChromeDriver);
    }

    @Test(enabled = false)
    public void test_get_remote_driver_instance () {
        WebDriver driver = driverFactory.getDriver("remote");
        driver.get("http://www.google.com");
        assertThat(driver.getTitle(), is("Google"));
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
        if (local != null) {
            driverFactory.closeDriver();
            local = null;
        }
        assertNull(local, "browser launched by WebDriver still open!!!");
    }

    @Test
    public void test_local_driver_created_if_unsupported_driver_type_is_specified () {
        WebDriver driver = driverFactory.getDriver("mobile");
        assertTrue(driver instanceof ChromeDriver);
    }

    @Test(expectedExceptions = CloneNotSupportedException.class)
    public void test_cannot_clone() throws CloneNotSupportedException {
        driverFactory.clone();
    }
}