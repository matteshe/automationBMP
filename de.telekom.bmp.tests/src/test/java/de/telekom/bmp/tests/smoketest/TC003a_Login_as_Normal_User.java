package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@UseWebDriver
@Test(groups = {"qcid-5506"})
public class TC003a_Login_as_Normal_User {

    @Inject
    BmpApplication app;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    // Needed user
    User user;

    @BeforeTest
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("mybmptestuser+normaluser@gmail.com").get();

        assertNotNull(user, "cannot find a valid user");

//        user.valid = false;
        navigateTo(login);

    }

    @Test
    public void test_003a_Login_as_Normal_User() {

        try {

            // verlangt Passwort über https://portal.bmptest.de von Aussen
            //click(header.loginBtn);
            
            //login.username.set(user.email);
            set(login.usernameInput, user.email);

            //login.password.set(user.password);
            set(login.passwordInput, user.password);

            //login.signin.click();
            click(login.signinBtn);

// WORKAROUND WEGEN CMS Redirect
            navigateTo(home);
            //assertThat(home, isCurrentPage());

            //header.account.click();
            click(header.accountMenu.logoutLnk);

            //header.logout.click();
            //click(header.logout);

            assertThat(home, isCurrentPage());

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
