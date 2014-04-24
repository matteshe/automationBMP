package de.telekom.bmp.functional;

import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.FunctionalActionsBase;
import static de.telekom.testframework.selenium.Matchers.innerHTML;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static de.telekom.testframework.selenium.Matchers.textContent;
import javax.inject.Inject;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

/**
 *
 * @author Daniel
 */
public class FunctionalActions extends FunctionalActionsBase {

    @Inject
    Login login;

    @Inject
    Header header;

    @Inject
    Home home;

    public void login(final String username, final String password) {
        handle("login", new Runnable() {

            @Override
            public void run() {
                navigateTo(login);

                set(login.username, username);
                set(login.password, password);
                click(login.signin);
            }
        }, username, password);

    }

    public void login(User user) {
        login(user.email, user.password);
    }

    public void logout() {
        handle("logout", new Runnable() {

            @Override
            public void run() {
                navigateTo(home);
                click(header.account.logout);
            }
        });
    }

    public void ensureGermanLanguageIsSet() {
        handle("ensureGermanLanguageIsSet", new Runnable() {

            @Override
            public void run() {

                int tries = 0;
                while (!checkThat(header.currentLanguage, textContent(containsString("deutsch")))) {

                    if (tries > 10) {
                        throw new RuntimeException("can't set the language to german");
                    }

                    click(header.toggleLanguage);
                    waitUntil(login, is(loaded()));
                    tries++;
                }
            }
        });
    }
}
