package de.telekom.bmp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import de.telekom.testframework.configuration.Configuration;
import de.telekom.testframework.selenium.Application;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Daniel
 */
@Singleton
public class BmpApplication extends Application {

    class MyConfiguration {

        @Inject(optional = true)
        @Named("BmpApplication.url")
        public String url = "https://toon:HullyGully@testcloud.bmptest.de";
        //public String url = "https://toon:HullyGully@verbundcloud.bmptest.de";

    }

    MyConfiguration configuration = Configuration.create(MyConfiguration.class);

    @Inject
    public BmpApplication(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getURLString() {
        return configuration.url;
    }

}
