package io.sandeep.framework.testng.pages;

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
public class LoginPage extends BasePageObject {
    @FindBy(id = "username")
    private WebElement usernameTxt;
    @FindBy(name = "j_password")
    private WebElement passwordTxt;
    @FindBy(css = "input.secondary-btn.btn")
    private WebElement btnLogin;
    @FindBy(className = "forgot-link")
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
        return By.className("forgot-link");
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