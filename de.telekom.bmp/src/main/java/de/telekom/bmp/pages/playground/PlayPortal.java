package de.telekom.bmp.pages.playground;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Link;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

/**
 *
 * @author Daniel Biehl
 */
@Singleton
@Path("/")
public class PlayPortal extends Page {

    @Inject
    public PlayPortal(BmpApplication application) {
        super(application);
    }
    
    @FindBy(xpath="//ul[@class='csc-menu csc-menu-19']//a")
    public List<Link> categories;
    
    @FindBy(xpath="//ul[@class='csc-menu csc-menu-19']//a[contains(@title, '%s')]")
    public Parameterized<Link> category;
}
