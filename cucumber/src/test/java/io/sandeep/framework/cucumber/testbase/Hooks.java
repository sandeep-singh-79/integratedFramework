package io.sandeep.framework.cucumber.testbase;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.sandeep.framework.core.config.FrameworkConfig;
import io.sandeep.framework.core.util.Utils;
import io.sandeep.framework.cucumber.context.TestContext;
import io.sandeep.framework.cucumber.enums.Context;
import io.sandeep.framework.pages.LoginPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

@Slf4j
public class Hooks {
    private TestContext testContext;
    private WebDriver driver;

    public Hooks (TestContext context) {
        testContext = context;
        Properties config = FrameworkConfig.getInstance().getConfigProperties();
        driver = context.getWebDriverManager().getDriver(config.getProperty("DRIVERTYPE", "local"));
    }

    @Before(order = 1)
    public void beforeScenario (Scenario scenario) {
        log.info("launching scenario {}...", scenario.getName());
        navigate_to((String) testContext.getScenarioContext().getContext("url"));
    }

    @After(order = 1)
    public void afterScenario (Scenario scenario) {
        log.info("Scenario: {} completed with result: {}", scenario.getName(), scenario.getStatus());
        if (scenario.isFailed()) {
            Utils.take_screenshot(driver, scenario.getName());
        }
    }

    private void navigate_to (String environment_url) {
        log.info("Loading URL: {}", environment_url);
        driver.navigate().to(environment_url);

        LoginPage loginPage = new LoginPage(driver);
        testContext.getScenarioContext().setContext(Context.PAGE_OBJECTS.LOGIN.toString(), loginPage);
    }
}
