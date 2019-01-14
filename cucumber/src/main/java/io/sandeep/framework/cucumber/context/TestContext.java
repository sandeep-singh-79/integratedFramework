package io.sandeep.framework.cucumber.context;

import io.sandeep.framework.core.driver.WebDriverFactory;

public class TestContext {
    private WebDriverFactory webDriverManager;
    private ScenarioContext scenarioContext;

    public TestContext () {
        webDriverManager = WebDriverFactory.getInstance();
        scenarioContext = ScenarioContext.getInstance();
    }

    public WebDriverFactory getWebDriverManager () {
        return webDriverManager;
    }

    public ScenarioContext getScenarioContext () {
        return scenarioContext;
    }
}