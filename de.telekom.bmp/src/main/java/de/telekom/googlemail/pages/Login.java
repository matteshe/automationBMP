package de.telekom.googlemail.pages;

import de.telekom.googlemail.GoogleMailApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.CheckBox;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Mathias Herkt
 */
@Singleton
@Path("")
public class Login extends Page {

    @Inject
    public Login(GoogleMailApplication app) {
        super(app);
    }

    @FindBy(name = "Email")
    public TextField email;

    @FindBy(name = "Passwd")
    public TextField password;

    @FindBy(name = "signIn")
    public Button signIn;

    @Deprecated
    @FindBy(xpath = "//a[contains(@href, 'logout')]")
    public Link signoutLink;

    @FindBy(name = "PersistentCookie")
    public CheckBox stayLoggedIn;

}
