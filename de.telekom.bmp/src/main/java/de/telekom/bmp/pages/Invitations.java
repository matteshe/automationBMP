package de.telekom.bmp.pages;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.TextField;

/**
 *
 * @author Mathias Herkt
 *
 */
@Singleton
@Path("/invitations")
public class Invitations extends Page {

    @Inject
    public Invitations(BmpApplication application) {
        super(application);
    }

    @FindBy(name = "firstName")
    public TextField firstName;

    @FindBy(name = "lastName")
    public TextField lastName;

    @FindBy(name = "passwordBorder:password")
    public TextField password;

    @FindBy(name = "confirmPassword")
    public TextField confirmPassword;

    @FindBy(name = "register")
    public Button signup;

    @FindBy(className = "reject")
    public Button reject;
}
