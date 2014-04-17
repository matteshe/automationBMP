package de.telekom.bmp.pages.account;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.pages.Header.AccountLink;
import de.telekom.testframework.selenium.Mask;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Form;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.controls.TextField;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */
@Singleton
@Path("/account/home")
public class Dashboard extends Page {

    @Inject
    public Dashboard(BmpApplication app) {
        super(app);
    }

    public static class InviteColleages extends Container {

        public InviteColleages(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(id = "inviteMore")
        public Button inviteMore;

        @FindBy(name = "email")
        public TextField email;

        @FindBy(name = ":submit")
        public Button submit;

        @FindBy(xpath = ".//*[contains(@class, 'feedbackPanelINFO')]")
        public Text info;

        @FindBy(xpath = ".//*[contains(@class, 'feedbackPanelERROR')]")
        public Text error;
    }

    @FindBy(id = "singleUserInvite")
    public InviteColleages inviteColleages;
}
