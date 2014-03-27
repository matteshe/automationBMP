package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.FindByProperty;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.Properties;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Properties
@Path("/signup")
@UseParent
public class Test1 extends Page {

    @Inject
    public Test1(BmpApplication app) {
        super(app);
    }
   
    @FindBy(id = "login_pzzz")
    public WebElement parent;
    
    @FindByProperty
    public TextField abc;
    
    @FindByProperty("emailaddress")
    @FindBy(name = "emailAddress")
    public TextField emailAddress;

    @FindByProperty("signup")
    @FindBy(name = "userSignupButton")
    public Button signup;

    @FindBy(xpath = "//input[@name='emailAddress']/../*[@class='icon valid']")
    public WebElement iconValid;

    @FindBy(xpath = "//input[@name='emailAddress']/../*[@class='icon invalid']")
    public WebElement iconInvalid;
}
