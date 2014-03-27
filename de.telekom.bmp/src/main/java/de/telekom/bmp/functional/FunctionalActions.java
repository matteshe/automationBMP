package de.telekom.bmp.functional;

import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Login;
import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Actions.set;
import javax.inject.Inject;

/**
 *
 * @author Daniel
 */
public class FunctionalActions {

    @Inject
    Login login;

    @Inject
    Header header;

    public void login(String username, String password) {
        navigateTo(login);

        set(login.usernameInput, username);
        set(login.passwordInput, password);
        click(login.signinBtn);
    }

    public void logout() {
        click(header.accountMenu.logoutLnk);
    }
}
