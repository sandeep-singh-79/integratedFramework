package io.sandeep.framework.core.driver;

import io.sandeep.framework.core.config.FrameworkConfig;
import io.sandeep.framework.core.driver.remote.Server;
import io.sandeep.framework.core.exception.NoSuchDriverException;
import io.sandeep.framework.core.exception.NoSuchServerRoleException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.io.Serializable;
import java.net.PortUnreachableException;
import java.util.Arrays;
import java.util.Properties;

import static java.lang.String.format;

@Slf4j
public final class WebDriverFactory implements Serializable, Cloneable {
    private static WebDriverFactory instance;
    private WebDriver localDriverInstance, remoteDriverInstance;
    private ThreadLocal <WebDriver> driver;
    private final Properties config;
    private Server remote_server;

    // webdriver instantiation properties
    private final String browser;

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
            try {
                switch (driverType.toLowerCase()) {
                    case "local": {
                        return getLocalDriverInstance();
                    }
                    case "remote": {
                        try {
                            return getRemoteDriverInstance();
                        } catch (NoSuchDriverException | PortUnreachableException e) {
                            log.error(format("No driver could be created for the grid remote. Please see logs for more info."));
                        } catch (WebDriverException e) {
                            e.printStackTrace();
                        }
                    }
                    default:
                        throw new NoSuchDriverException(format("UnSupported driver type requested: %s", driverType));
                }
            } catch (NoSuchDriverException e) {
                log.error("Encountered issue while instantiating driver instance {}", driverType);
                log.error(Arrays.toString(e.getStackTrace()));
                log.info("creating the driver for chrome by default");
                return getLocalDriverInstance();
            }
        });

        return driver.get();
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

    private WebDriver getRemoteDriverInstance () throws NoSuchDriverException, WebDriverException, PortUnreachableException {
        String serverAddress = System.getProperty("host", config.getProperty("remote.ip"));
        int serverPort = Integer.parseInt(System.getProperty("port", config.getProperty("remote.port")));
        String version = System.getProperty("version", config.getProperty("remote.version"));
        Platform platform = Platform.fromString(System.getProperty("platform", config.getProperty("remote.platform")));

        if (remoteDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (remoteDriverInstance == null) {
                    if (StringUtils.isBlank(serverAddress)) {
                        serverAddress = "localhost";
                    }
                    if (StringUtils.isBlank(Integer.toString(serverPort)) || serverPort < 1) {
                        try {
                            serverPort = Server.get_free_port();
                        } catch (IOException e) {
                            log.error("Unable to retrieve random free port for selenium server launch!");
                            throw new PortUnreachableException("Unable to retrieve random free port for selenium server launch!");
                        }
                    }

                    if (!RemoteDriver.is_remote_server_alive(serverAddress, serverPort)) {
                        log.error("no response from server for url {} at port {}", serverAddress, serverPort);
                        log.info("Launching selenium server on the localhost at port {}", serverPort);

                        try {
                            remote_server = new Server(serverAddress, serverPort, "hub");
                            serverPort = remote_server.start();
                        } catch (NoSuchServerRoleException e) {
                            log.error("Unable to initialize the remote selenium server on ");
                        }
                    }

                    remoteDriverInstance = new RemoteDriver(browser, serverAddress, serverPort, platform, version).createDriver();
                }
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
        throw new CloneNotSupportedException(format("Cloning not allowed for %s class", WebDriverFactory.class));
    }

    public Object readResolve () {
        return instance;
    }
}
