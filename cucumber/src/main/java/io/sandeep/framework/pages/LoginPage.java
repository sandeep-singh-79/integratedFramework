package io.sandeep.framework.pages;

import static org.openqa.selenium.support.PageFactory.initElements;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.sandeep.framework.core.config.FrameworkConfig;
import io.sandeep.framework.core.pages.base.BasePageObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPage extends BasePageObject {
    @FindBy(name = "username")
    private WebElement usernameTxt;
    @FindBy(name = "password")
    private WebElement passwordTxt;
    @FindBy(css = "#loginPanel > form > div:nth-child(5) > input")
    private WebElement btnLogin;
    @FindBy(css = "#loginPanel > p:nth-child(2) > a")
    private WebElement lnkForgotPassword;


    public LoginPage (WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public LoginPage (WebDriver driver, Properties config) {
        super(driver, config);
        initElements(ajaxElementLocatorFactory, this);
    }

    @Override
    protected By getUniqueElement () {
        return By.cssSelector("#loginPanel > p:nth-child(2) > a");
    }

    public LoginErrorPage attemptSignIn (String username, String password) {
        enterText(usernameTxt, username);
        enterText(passwordTxt, password);
        btnLogin.click();

        return new LoginErrorPage(driver);
    }

    public ForgotPasswordPage navigateToLostPassword () {
        lnkForgotPassword.click();

        return new ForgotPasswordPage(driver);
    }
}