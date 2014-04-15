package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Form;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.controls.TextField;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("/signup")
public class Signup extends Page {

    @Inject
    public Signup(BmpApplication app) {
        super(app);
    }

    @FindBy(xpath = "//div[class=signup-form]//div[contains(@class, 'first')")
    public WebElement registerHeader;

    @FindBy(xpath = "//div[contains(@class, 'registCode')")
    public WebElement registCode;

    @FindBy(xpath = "//div[contains(@class, 'DTLink')")
    public Text alreadyRegistered;

    public static class SignupForm extends Form {

        public SignupForm(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }
        @FindBy(name = "emailAddress")
        public TextField emailAddress;

        @FindBy(name = "userSignupButton")
        public Button signup;

        @FindBy(xpath = "//input[@name='emailAddress']/../*[@class='icon valid']")
        public WebElement iconValid;

        @FindBy(xpath = "//input[@name='emailAddress']/../*[@class='icon invalid']")
        public WebElement iconInvalid;
    }

    @FindBy(name = "signupForm")
    public SignupForm signupForm;

    public static class SignupConfirmationPanel extends Container {

        public SignupConfirmationPanel(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = "./*[contains(@class, 'thanks')]")
        public Text thanks;
    }

    @FindBy(className = "signupConfirmationPanel")
    public SignupConfirmationPanel signupConfirmationPanel;
}
