package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("/home")
public class Home extends Page {

    @Inject
    public Home(BmpApplication app) {
        super(app);
    }    
    
    @FindBy(xpath = "//a[@href='./signup']")
    public WebElement register;
    
}
