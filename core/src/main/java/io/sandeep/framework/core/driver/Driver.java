package io.sandeep.framework.core.driver;

import io.sandeep.framework.core.config.FrameworkConfig;
import io.sandeep.framework.core.exception.NoSuchDriverException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.util.Properties;

@Slf4j
abstract class Driver {
    protected WebDriver driver;
    protected String browser = null;
    protected Platform platform = null;
    protected String version = null;
    protected String serverAddress = null;
    protected int serverPort = 4444;

    protected Properties config;
    protected Logger logger;

    Driver () {
        config = FrameworkConfig.getInstance().getConfigProperties();
    }

    abstract WebDriver createDriver () throws NoSuchDriverException;
}