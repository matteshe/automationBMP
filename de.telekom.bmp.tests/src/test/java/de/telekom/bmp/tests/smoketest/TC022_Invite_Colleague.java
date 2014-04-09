package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MyApps;
import de.telekom.bmp.pages.account.Dashboard;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import java.util.Date;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@QCId("5145")
@UseWebDriver
public class TC022_Invite_Colleague {

    @Inject
    BmpApplication app;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    Header header;

    @Inject
    Dashboard dashboardPage;

    @Inject
    MyApps myApps;

    @Inject
    Datapool datapool;

// Needed user
    private User user;

    @BeforeTest
    public void setup() {
        // Login with a normal User
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("mybmptestuser+normaluser@gmail.com").get();

        assertNotNull(user, "cannot find a valid user");

//        user.valid = false;
        navigateTo(login);

    }

    @Test
    public void test_022_Invite_Colleague() throws InterruptedException {

        try {
            set(login.usernameInput, user.email);

            set(login.passwordInput, user.password);

            click(login.signinBtn);

            click(header.settingsMenu.accountLnk);

// WORKAROUND BECAUSE OF CMS
//            navigateTo(myApps);
//
//            assertThat(myApps, currentPage());
            String emailtoInvite = "mybmptestuser+toInvite" + new Date().getTime() + "@gmail.com";
            set(dashboardPage.inviteEmailInput, emailtoInvite);
            click(dashboardPage.singleInviteBtn);
            assertThat(dashboardPage.inviteSuccessfullTxt.isDisplayed());

            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());

            Thread.sleep(5000);
            
            createUserToSave();
//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

    private void createUserToSave() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
