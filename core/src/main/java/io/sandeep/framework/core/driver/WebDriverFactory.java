package io.sandeep.framework.core.driver;

import io.sandeep.framework.core.config.FrameworkConfig;
import io.sandeep.framework.core.exception.NoSuchDriverException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
public final class WebDriverFactory implements Serializable, Cloneable {
    private static WebDriverFactory instance;
    private WebDriver localDriverInstance, remoteDriverInstance;
    private ThreadLocal <WebDriver> driver;
    private Properties config;

    // webdriver instantiation properties
    private String browser;

    private WebDriverFactory () {
        config = FrameworkConfig.getInstance().getConfigProperties();
        browser = System.getProperty("browser", config.getProperty("BROWSER"));
    }

    public static WebDriverFactory getInstance () {
        if (instance == null) {
            synchronized (WebDriverFactory.class) {
                if (instance == null) {
                    instance = new WebDriverFactory();
                }
            }
        }

        return instance;
    }

    public WebDriver getDriver (String driverType) throws NullPointerException {
        driver = ThreadLocal.withInitial(() -> {
            WebDriver tempDriver = null;
            try {
                switch (driverType.toLowerCase()) {
                    case "local": {
                        tempDriver = getLocalDriverInstance();
                        break;
                    }
                    case "remote": {
                        tempDriver = getRemoteDriverInstance();
                        break;
                    }
                    default:
                        throw new NoSuchDriverException(String.format("UnSupported sandeep.io.framework.core.driver type requested: %s", driverType));
                }
            } catch (NoSuchDriverException e) {
                log.error("Encountered issue while instantiating driver instance {}", driverType);
                log.error(Arrays.toString(e.getStackTrace()));
                log.info("creating the driver for chrome by default");
                tempDriver = getLocalDriverInstance();
            }

            return tempDriver;
        });

        return (driver != null ? driver.get() : null);
    }

    private WebDriver getLocalDriverInstance () {
        if (localDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (localDriverInstance == null)
                    localDriverInstance = new LocalDriver(browser).createDriver();
            }
        }
        return localDriverInstance;
    }

    private WebDriver getRemoteDriverInstance () throws NoSuchDriverException {
        String serverAddress = config.getProperty(System.getProperty("host"), "remote.ip");
        int serverPort = Integer.parseInt(config.getProperty(System.getProperty("port"), "remote.port"));
        String version = config.getProperty(System.getProperty("version"), "remote.version");
        Platform platform = Platform.fromString(config.getProperty(System.getProperty("platform"), "remote.platform"));

        if (remoteDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (remoteDriverInstance == null)
                    remoteDriverInstance = new RemoteDriver(browser, serverAddress, serverPort, platform, version)
                                                   .createDriver();
            }
        }

        return remoteDriverInstance;
    }

    public void closeDriver () {
        if (driver.get() != null) {
            driver.get().quit();
            driver = null;
        }
    }

    @Override
    public Object clone () throws CloneNotSupportedException {
        log.info("Not allowed to clone the current class");
        log.info("throwing CloneNotSupportedException for your pains ...");
        throw new CloneNotSupportedException(String.format("Cloning not allowed for %s class", WebDriverFactory.class));
    }

    public Object readResolve () {
        return instance;
    }
}
