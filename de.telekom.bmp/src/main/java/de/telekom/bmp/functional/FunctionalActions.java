package de.telekom.bmp.functional;

import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.FunctionalActionsBase;
import static de.telekom.testframework.selenium.Matchers.innerHTML;
import static de.telekom.testframework.selenium.Matchers.loaded;
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

                // toggle the language till "deutsch" is the current language
                while (!checkThat(header.currentLanguage, innerHTML(containsString("deutsch")))) {
                    click(header.toggleLanguage);
                    waitUntil(login, is(loaded()));
                }
            }
        });
    }
}
