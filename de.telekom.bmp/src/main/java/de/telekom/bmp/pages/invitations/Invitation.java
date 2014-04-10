package de.telekom.bmp.pages.invitations;

import de.telekom.bmp.BmpApplication;
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
@Path("/invitations")
public class Invitation extends Page {

    @Inject
    public Invitation(BmpApplication app) {
        super(app);
    }

    @FindBy(name = "firstName")
    public TextField firstNameInput;
    
    @FindBy(name = "lastName")
    public TextField lastNameInput;
    
    @FindBy(name = "passwordBorder:password")
    public TextField passwordInput;
    
    @FindBy(name = "confirmPassword")
    public TextField confirmPasswordInput;
    
    @FindBy(name = "register")
    public Button signupBtn;
}
