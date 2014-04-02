package de.telekom.testframework.selenium.guice;

import com.google.inject.AbstractModule;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
                
        driver = new FirefoxDriver();

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.MINUTES);

        //driver.manage().window().maximize();
        bind(WebDriver.class).toInstance(driver);
    }

}
