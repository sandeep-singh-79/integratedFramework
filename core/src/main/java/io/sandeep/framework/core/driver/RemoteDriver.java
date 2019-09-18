package io.sandeep.framework.core.driver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.sandeep.framework.core.exception.NoSuchDriverException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Slf4j
class RemoteDriver extends Driver {
    RemoteDriver () {
        super();
    }

    RemoteDriver (String browser, String serverAddress, int serverPort, Platform platform, String version) {
        this();

        this.browser = browser;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.platform = platform;
        this.version = version;
    }

    @Override
    public WebDriver createDriver () throws NoSuchDriverException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (driver == null) {
            if (browser.equals("firefox")) {
                capabilities.setBrowserName("firefox");
            } else if (browser.equals("internetExplorer") || browser.contains("internet") || browser.contains("iexplore")) {
                capabilities.setBrowserName("ie");
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            } else if (browser.equals("chrome")) {
                capabilities.setBrowserName("chrome");
            } else {
                throw new NoSuchDriverException("Browser type unsupported");
            }

            capabilities.setVersion(version);
            capabilities.setPlatform(platform);
            capabilities.setAcceptInsecureCerts(true);
            capabilities.setJavascriptEnabled(true);
            try {
                WebDriverManager.getInstance(DriverManagerType.SELENIUM_SERVER_STANDALONE).setup();
                driver = (new RemoteWebDriver(
                        new URL(String.format("http://%s:%d/wd/hub", serverAddress, serverPort)), capabilities));
            } catch (MalformedURLException e) {
                log.error("URL of the server was not found or properly formatted!");
                log.error(e.getMessage());
                log.error(Arrays.toString(e.getStackTrace()));
            }
        }

        return driver;
    }
}
