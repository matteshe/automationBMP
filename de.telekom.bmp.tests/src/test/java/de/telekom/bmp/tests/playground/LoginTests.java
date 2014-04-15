package de.telekom.bmp.tests.playground;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;

import de.telekom.bmp.pages.*;

import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */

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

    @Inject
    Portal portal;

    @Inject
    Listing listing;
    
    @Test
    public void theTest() {
        User user = datapool.users()
                .field("valid").equal(true)
                .field("registered").equal(true)
                .field("role").equal(UserRole.USER).get();

        assertThat("we have a valid user", user != null);

        navigateTo(login);
        waitUntil(login, isCurrentPage());

//        set(login.username, "tester.bmp+user001@gmail.com");
//        set(login.password, "tester123");
        set(login.usernameInput, user.email);
        set(login.passwordInput, user.password);

        click(login.signinBtn);

        //verifyThat(myApps, page());
        set(header.searchInput, "test");
        click(header.searchBtn);

        waitUntil(listing, isCurrentPage());

        waitUntil(listing.appInfos, not(empty()));

        listing.reportScreenShot();

        for (Listing.AppInfo appinfo : listing.appInfos) {
            System.out.println(appinfo.appName.getText() + " " + appinfo.prize.getText());
        }

        click(listing.appInfo.get("Test App 2012_SAK").appName);

        click(header.accountMenu.logoutLnk);
    }

}
