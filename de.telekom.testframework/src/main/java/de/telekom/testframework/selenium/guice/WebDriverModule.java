package de.telekom.testframework.selenium.guice;

import com.google.inject.AbstractModule;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.SeleniumConfiguration;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 *
 * @author Daniel Biehl
 */
public class WebDriverModule extends AbstractModule {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverModule() {

    }

    @Override
    protected void configure() {
//        DesiredCapabilities des = DesiredCapabilities.internetExplorer();
//        driver = new RemoteWebDriver(des);

        // TODO enable/disable setting for HelperProfile
        FirefoxProfile profile = new FirefoxProfile();
        try {
            profile.addExtension(WebDriverModule.class, "helpers/FirefoxHelper.xpi");
        } catch (IOException ex) {

        }

        FirefoxDriver firefoxDriver = new FirefoxDriver(profile);
        driver = firefoxDriver;

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(SeleniumConfiguration.current.implicitlyWait, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(SeleniumConfiguration.current.pageLoadTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(SeleniumConfiguration.current.scriptTimeout, TimeUnit.MINUTES);

        //driver.manage().window().maximize();
        bind(WebDriver.class).toInstance(driver);

        requestStaticInjection(Browser.class);
    }

}
