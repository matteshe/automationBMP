package de.telekom.bmp.pages;

import de.telekom.bmp.GoogleApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("")
public class GoogleLoginPage extends Page {

    @Inject
    public GoogleLoginPage(GoogleApplication app) {
        super(app);
    }
    
    @FindBy(name = "Email")    
    public TextField email;
    
    @FindBy(name = "Passwd")    
    public TextField password;
    
    @FindBy(name = "signIn")
    public Button loginBtn;
}
