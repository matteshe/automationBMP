package de.telekom.bmp.tests.smoketest;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;

import de.telekom.bmp.pages.*;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@QCId("5563")
@UseWebDriver
public class LoginTests {

    @Inject
    Datapool datapool;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    MyApps myApps;

    @Inject
    Header header;

    @Test
    public void theTest() {
        User user = datapool.users().field("registered").equal(true).field("role").equal(UserRole.USER).get();
        assertThat("we have a valid user", user != null);

        navigateTo(login);

//        set(login.username, "tester.bmp+user001@gmail.com");
//        set(login.password, "tester123");

        set(login.usernameInput, user.email);
        set(login.passwordInput, user.password);

        click(login.signinBtn);

        verifyThat(myApps, isCurrentPage());

        click(header.accountMenu.logoutLnk);
        
    }

}
