package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("/login")
public class Login extends Page {

    @Inject
    public Login(BmpApplication app) {
        super(app);
    }
 
    @FindBy(id = "username")
    public TextField username;
    
    @FindBy(id = "password")
    public TextField password;
    
    @FindBy(name = "signin")   
    public Button signin;
    
    @FindBy(xpath = "//a[@href='./signup']")    
    public Link signuplink;
}
