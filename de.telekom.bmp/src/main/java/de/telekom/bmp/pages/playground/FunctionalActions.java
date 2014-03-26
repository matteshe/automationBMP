package de.telekom.bmp.pages.playground;

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
    PlayLogin login;

    @Inject
    PlayHeader header;

    public void login(String username, String password) {
        navigateTo(login);

        set(login.username, username);
        set(login.password, password);
        click(login.signin);
    }

    public void logout() {
        click(header.account.logout);
    }
}
