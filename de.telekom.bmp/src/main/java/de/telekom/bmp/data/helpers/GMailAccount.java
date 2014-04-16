package de.telekom.bmp.data.helpers;

import de.telekom.bmp.data.EMailAccount;
import de.telekom.bmp.data.User;
import de.telekom.googlemail.GoogleMailApplication;
import de.telekom.googlemail.pages.Login;
import de.telekom.googlemail.pages.Mail;
import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.closeWindow;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Actions.newWindow;
import static de.telekom.testframework.Actions.page;
import static de.telekom.testframework.Actions.sendKeys;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.Assert.waitUntil;
import static de.telekom.testframework.Assert.waitUntil;
import static de.telekom.testframework.Assert.waitUntil;
import static de.telekom.testframework.Assert.waitUntil;
import static de.telekom.testframework.Assert.waitUntil;
import de.telekom.testframework.selenium.Browser;
import static de.telekom.testframework.selenium.Matchers.exists;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static de.telekom.testframework.selenium.Matchers.text;
import static java.util.concurrent.TimeUnit.MINUTES;
import javax.inject.Inject;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.is;
import org.mongodb.morphia.annotations.Entity;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@Entity("EMailAccount")
public class GMailAccount extends EMailAccount {

    public String username;
    public String password;

    public GMailAccount() {

    }

    public GMailAccount(String email, String password) {
        super(email);
        this.username = email;
        this.password = password;
    }

    GoogleMailApplication googleApplication;
    Login login;
    Mail mail;

    @Test
    public void login() {
        navigateTo(login);
        set(login.email, "mybmptestuser@gmail.com");
        set(login.password, "galerien3?");
        set(login.stayLoggedIn, false);
        click(login.signIn);

        waitUntil(page(), is(loaded()), 1, MINUTES);
    }

    public String searchMail(User user) {
        navigateTo(mail);

        set(mail.q, user.email);

        sendKeys(mail.q, Keys.ENTER);

        for (int i = 0; i < mail.mails.getRows().size(); i++) {

            Mail.Mails.MailRow row = mail.mails.rows.get(i);

            click(row.open);

            if (mail.msg.accountSetupLink.exists()) {
                break;
            }
            Browser.getInstance().navigate().back();
        }

        assertThat(mail.msg.accountSetupLink, exists());
        verifyThat(mail.msg.accountSetupLink, text(containsString("accountSetup")));

        return mail.msg.accountSetupLink.getText();
    }

    public void logout() {
        click(mail.logout);
    }

    @Override
    public String getConfirmLink(User user) {
        googleApplication = new GoogleMailApplication(Browser.getInstance().getWebDriver());
        login = new Login(googleApplication);
        mail = new Mail(googleApplication);

        newWindow();
        try {
            login();
            try {
                return searchMail(user);
            } finally {
                logout();
            }
        } finally {
            closeWindow();
        }
    }

}
