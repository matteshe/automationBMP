package de.telekom.bmp.pages.superuser;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Link;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("/superuser/dashboard")
public class DashboardPage extends Page {

    @Inject
    public DashboardPage(BmpApplication app) {
        super(app);
    }

    @FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[2]/a")
    public Link exceptionsTab;
    
}
