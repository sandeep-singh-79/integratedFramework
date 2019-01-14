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
public class LoginErrorPage extends BasePageObject {
    @FindBy(className = "title")
    private WebElement errorTitle;
    @FindBy(className = "subtitle")
    private WebElement errorTxt;

    public LoginErrorPage (WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public LoginErrorPage (WebDriver driver, Properties config) {
        super(driver, config);
        initElements(ajaxElementLocatorFactory, this);
    }

    @Override
    protected By getUniqueElement () {
        return By.xpath("//*[@class='title']");
    }

    public String getErrorText () {
        return errorTxt.getText();
    }
}