package de.telekom.googlemail.pages.tests;

import de.telekom.googlemail.pages.Login;
import de.telekom.googlemail.pages.Mail;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.selenium.Browser;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static java.util.concurrent.TimeUnit.MINUTES;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@UseWebDriver
public class GoogleMailTests {

    @Inject
    Login login;

    @Inject
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

    @Test(dependsOnMethods = "login")
    public void searchMail() {
        navigateTo(mail);

        set(mail.q, "mybmptestuser+1397592486226@gmail.com");
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
    }

    @Test(dependsOnMethods = "searchMail")
    public void logout() {
        click(mail.logout);
    }
}
