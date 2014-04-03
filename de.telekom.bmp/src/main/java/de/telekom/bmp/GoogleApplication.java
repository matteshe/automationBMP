package de.telekom.bmp;

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
public class GoogleApplication extends Application {

    class MyConfiguration extends Configuration {

        @Inject(optional = true)
        @Named("GoogleApplication.url")
        public String url = "http://www.google.de";

        public MyConfiguration() {
            initialize();
        }
    }

    MyConfiguration configuration = new MyConfiguration();

    @Inject
    public GoogleApplication(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getURLString() {
        return configuration.url;
    }
}
