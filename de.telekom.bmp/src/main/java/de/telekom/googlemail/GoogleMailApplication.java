package de.telekom.googlemail;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import de.telekom.testframework.configuration.Configuration;
import de.telekom.testframework.selenium.Application;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Mathias Herkt
 */
@Singleton
public class GoogleMailApplication extends Application {

    class MyConfiguration extends Configuration {

        @Inject(optional = true)
        @Named("GoogleApplication.url")
        public String url = "https://mail.google.com";

        public MyConfiguration() {
            initialize();
        }
    }

    MyConfiguration configuration = new MyConfiguration();

    @Inject
    public GoogleMailApplication(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getURLString() {
        return configuration.url;
    }
}
