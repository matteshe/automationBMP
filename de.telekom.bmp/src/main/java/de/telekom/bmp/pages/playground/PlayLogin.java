
package de.telekom.bmp.pages.playground;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Application;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.TextField;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("/login")
public class PlayLogin extends Page {


    @Inject
    public PlayLogin(BmpApplication application) {
        super(application);
    }
    
    public TextField username;
    public TextField password;
    
    @FindBy(name="signin")
    public Button signin;
    
    @FindBy(tagName = "a")
    public List<Link> links;
    
    public WebElement test;
}
