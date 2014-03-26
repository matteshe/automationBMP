package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author Daniel
 */

@Singleton
@Path("/myapps")
public class MyApps extends Page {

    @Inject
    public MyApps(BmpApplication app) {
        super(app);
    }       
}
