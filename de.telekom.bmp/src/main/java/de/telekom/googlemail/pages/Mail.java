package de.telekom.googlemail.pages;

import de.telekom.googlemail.GoogleMailApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.CheckBox;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Row;
import de.telekom.testframework.selenium.controls.Table;
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
 * @author Daniel Biehl
 */
@Singleton
@Path("/mail/?ui=html&zy=h")
public class Mail extends Page {

    @Inject
    public Mail(GoogleMailApplication app) {
        super(app);
    }

    @FindBy(name = "q")
    public TextField q;

    @FindBy(xpath = "//a[contains(@href, 'logout')]")
    public Link logout;

    public static class Mails extends Table {

        public Mails(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        public static class MailRow extends Row {

            public MailRow(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
                super(driver, locator, webElement);
            }

            @FindBy(name = "t")
            public CheckBox marker;

            @FindBy(tagName = "a")
            public Link open;
        }

        @FindBy(xpath = "./tbody/tr")
        public List<MailRow> rows;
    }

    @FindBy(className = "th")
    public Mails mails;

    public static class Message extends Text {

        public Message(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = ".//a[contains(@href, 'accountSetup')]")
        public Link accountSetupLink;

        @FindBy(xpath = ".//a[contains(@href, 'invitations')]")
        public Link invitationsLink;
    }
    @FindBy(xpath = "//div[contains(@class, 'msg')]")
    public Message msg;
}
