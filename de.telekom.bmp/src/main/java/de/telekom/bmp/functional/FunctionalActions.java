package de.telekom.bmp.functional;

import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
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
    
    @Inject
    Home hPg;

    public void login(String username, String password) {
        navigateTo(login);

        set(login.usernameInput, username);
        set(login.passwordInput, password);
        click(login.signinBtn);
    }
    
    public void login(User user) {
    	login(user.email, user.password);
    }

    public void logout() {
    	navigateTo(hPg);
        click(header.accountMenu.logoutLnk);
    }
    
    public void ensureGermLanguageIsSet(){
        if (!header.toogle_EN_LanguageLnk.isDisplayed()) {
            click(header.toogle_EN_LanguageLnk);
        }

    }
}
