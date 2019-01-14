package io.sandeep.framework.pages;

import io.sandeep.framework.core.config.FrameworkConfig;
import io.sandeep.framework.core.pages.base.BasePageObject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Properties;

import static org.openqa.selenium.support.PageFactory.initElements;

@Slf4j
public class ForgotPasswordPage extends BasePageObject {
    @FindBy(id = "username-input")
    private WebElement txtUsername;
    @FindBy(id = "submit-button")
    private WebElement btnGetPassword;

    public ForgotPasswordPage (WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public ForgotPasswordPage (WebDriver driver, Properties config) {
        super(driver, config);
        initElements(ajaxElementLocatorFactory, this);
    }

    @Override
    protected By getUniqueElement () {
        return By.className("back-to-link");
    }

    public String getPageTitle () {
        return driver.getTitle();
    }
}