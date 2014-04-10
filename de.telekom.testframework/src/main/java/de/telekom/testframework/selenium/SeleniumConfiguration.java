package de.telekom.testframework.selenium;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.telekom.testframework.configuration.Configuration;

/**
 *
 * @author Daniel
 */
public class SeleniumConfiguration extends Configuration {

    protected SeleniumConfiguration() {
        initialize();
    }

    public static final SeleniumConfiguration current = new SeleniumConfiguration();

    @Inject(optional = true)
    @Named("Selenium.implicitlyWait")
    public long implicitlyWait = 30;

    @Inject(optional = true)
    @Named("Selenium.pageLoadTimeout")
    public long pageLoadTimeout = 30;

    @Inject(optional = true)
    @Named("Selenium.scriptTimeout")
    public long scriptTimeout = 30;

    @Inject(optional = true)
    @Named("Selenium.existsTimeout")
    public long existsTimeout = 2;

}
