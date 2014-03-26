package de.telekom.bmp.pages.playground;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.Properties;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Control;
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
@Properties
@Path("/signup")
public class Test1 extends Page {

    @Inject
    public Test1(BmpApplication app) {
        super(app);
    }

    @FindBy(name = "emailAddress")
    public TextField emailAddress;

    public Button signup;

    @FindBy(xpath = ".//input[@name='emailAddress']/../*[@class='icon valid']")
    public WebElement iconValid;

    @FindBy(xpath = ".//input[@name='emailAddress']/../*[@class='icon invalid']")
    public WebElement iconInvalid;

    @FindBy(id="__not_existing__")
    public Control notExisting;
    
    @FindBy(tagName = "a")
    public List<Link> links;

    @FindBy(linkText = "%s")
    public Parameterized<List<Link>> users;

    @FindBy(linkText = "%s")
    public Parameterized<Link> user;
    
    /**
     * Get's all links containing the given text
     */
    @FindBy(xpath = "//a[contains(text(), '%s')]")
    public Parameterized<List<Link>> paramslinks;

}
