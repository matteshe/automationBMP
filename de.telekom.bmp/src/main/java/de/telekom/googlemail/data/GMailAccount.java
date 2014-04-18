package de.telekom.googlemail.data;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.telekom.bmp.data.EMailAccount;
import de.telekom.bmp.data.User;
import de.telekom.googlemail.GoogleMailApplication;
import de.telekom.googlemail.pages.Login;
import de.telekom.googlemail.pages.Mail;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.FunctionalActionsBase;
import de.telekom.testframework.RunnableFunction;
import de.telekom.testframework.Wait;
import de.telekom.testframework.configuration.Configuration;
import de.telekom.testframework.selenium.Browser;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.controls.Link;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.hamcrest.Matchers.is;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;
import org.openqa.selenium.Keys;

/**
 *
 * @author Daniel
 */
@Entity("EMailAccount")
public class GMailAccount extends EMailAccount {

    static class MyConfiguration extends Configuration {

        MyConfiguration() {
            initialize();
        }

        @Inject(optional = true)
        @Named("GMailAccount.mailSearchTimeOutInMinutes")
        public int mailSearchTimeOutInMinutes = 5;
    }

    static MyConfiguration configuration = new MyConfiguration();

    public String username;
    public String password;

    public GMailAccount() {

    }

    public GMailAccount(String email, String password) {
        super(email);
        this.username = email;
        this.password = password;
    }

    @Transient
    transient GoogleMailApplication googleApplication;
    @Transient
    transient Login login;
    @Transient
    transient Mail mail;

    public void login(final User user) {
        FunctionalActionsBase.handle("login", new Runnable() {

            @Override
            public void run() {
                navigateTo(login);

                set(login.email, ((GMailAccount) user.emailAccount).username);
                set(login.password, ((GMailAccount) user.emailAccount).password);
                set(login.stayLoggedIn, false);
                click(login.signIn);
            }
        });

        waitUntil(page(), is(loaded()), 1, MINUTES);
    }

    public String searchMail(final User user, final Link link) {
        return FunctionalActionsBase.handle("searchMail", new RunnableFunction<String>() {

            @Override
            public String run() {
                navigateTo(mail);

                set(mail.q, user.email);

                sendKeys(mail.q, Keys.ENTER);

                boolean found = false;
                for (int i = 0; i < mail.mails.getRows().size() - 1; i++) {

                    Mail.Mails.MailRow row = mail.mails.rows.get(i);

                    click(row.open);

                    if (link.exists()) {
                        found = true;
                        break;
                    }
                    Browser.getInstance().navigate().back();
                }

                if (!found) {
                    return null;
                }

                return link.getHref();
            }
        }, user, link);
    }

    public void logout() {
        FunctionalActionsBase.handle("logout", new Runnable() {

            @Override
            public void run() {
                click(mail.logout);
                waitUntil(page(), is(loaded()), 1, MINUTES);
            }
        });
    }

    public String getLink(final User user, final Link link) {
        return FunctionalActionsBase.handle("getLink", new RunnableFunction<String>() {

            @Override
            public String run() {
                newWindow();
                try {
                    login(user);
                    try {
                        return Wait.until("cant't find a confirmation link", user, new Function<User, String>() {

                            @Override
                            public String apply(User input) {
                                return searchMail(input, link);
                            }
                        }, configuration.mailSearchTimeOutInMinutes, MINUTES);
                    } finally {
                        logout();
                    }
                } finally {
                    closeWindow();
                }
            }
        }, user, link);
    }

    @Override
    public String internalGetConfirmationLink(final User user) {
        return FunctionalActionsBase.handle("getConfirmationLink", new RunnableFunction<String>() {

            @Override
            public String run() {
                googleApplication = new GoogleMailApplication(Browser.getInstance().getWebDriver());
                login = new Login(googleApplication);
                mail = new Mail(googleApplication);
                return getLink(user, mail.msg.accountSetupLink);
            }
        }, user);
    }

    @Override
    protected String internalGetInvitationsLink(final User user) {
        return FunctionalActionsBase.handle("getInvitationsLink", new RunnableFunction<String>() {

            @Override
            public String run() {
                googleApplication = new GoogleMailApplication(Browser.getInstance().getWebDriver());
                login = new Login(googleApplication);
                mail = new Mail(googleApplication);
                return getLink(user, mail.msg.invitationsLink);
            }
        }, user);
    }
}
