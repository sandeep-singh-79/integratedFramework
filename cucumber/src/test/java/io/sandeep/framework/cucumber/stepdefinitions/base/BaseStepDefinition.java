package io.sandeep.framework.cucumber.stepdefinitions.base;

import io.sandeep.framework.cucumber.context.TestContext;
import io.sandeep.framework.cucumber.enums.Context;
import org.openqa.selenium.WebDriver;

public class BaseStepDefinition {
    protected WebDriver driver;
    protected TestContext context;

    public BaseStepDefinition (TestContext context) {
        this.context = context;
        driver = (WebDriver) context.getScenarioContext().getContext(Context.DRIVER.toString());
    }
}
