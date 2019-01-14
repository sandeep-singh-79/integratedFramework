package io.sandeep.framework.core.pages.base;

import io.sandeep.framework.core.config.FrameworkConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
import static org.testng.Assert.assertTrue;

@Slf4j
public abstract class BasePageObject {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected AjaxElementLocatorFactory ajaxElementLocatorFactory;

    private Properties config;
    private WebDriver.Timeouts timeout;

    public BasePageObject (WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public BasePageObject (WebDriver driver, Properties config) {
        this.driver = driver;
        this.config = config;
        timeout = driver.manage().timeouts();
        wait = new WebDriverWait(driver, parseInt(config.getProperty("WEBDRIVERWAIT_TIMEOUT")),
                parseInt(config.getProperty("WEBDRIVERWAIT_POLL")));
        ajaxElementLocatorFactory = new AjaxElementLocatorFactory(driver, parseInt(config.getProperty("LOCATOR_FACTORY_TIMEOUT")));
        setTimeouts();

        isLoaded();
    }

    private void setTimeouts () {
        timeout.implicitlyWait(parseInt(config.getProperty("IMPLICITWAIT_TIMEOUT")),
                TimeUnit.MILLISECONDS);
        timeout.pageLoadTimeout(parseInt(config.getProperty("PAGE_LOAD_TIMEOUT")),
                TimeUnit.MILLISECONDS);
    }

    /**
     * Each page object must implement this method to return the identifier of a unique WebElement on that page.
     * The presence of this unique element will be used to assert that the expected page has finished loading
     *
     * @return the By locator of unique element(s) on the page
     */
    protected abstract By getUniqueElement ();

    private void isLoaded () throws Error {
        //Define a list of WebElements that match the unique element locator for the page
        By uniqElement = getUniqueElement();
        List <WebElement> uniqueElement = driver.findElements(uniqElement);

        // Assert that the unique element is present in the DOM
        assertTrue((uniqueElement.size() > 0),
                String.format("Unique Element %s not found for %s", uniqElement.toString(), this.getClass().getSimpleName()));

        // Wait until the unique element is visible in the browser and ready to use. This helps make sure the page is
        // loaded before the next step of the tests continue.
        wait.until(ExpectedConditions.visibilityOfAllElements(uniqueElement));
    }

    protected void enterText (WebElement txtfield, String text) {
        txtfield.click();
        txtfield.clear();
        txtfield.sendKeys(text);
    }
}
