package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.functional.GoogleMailAccount;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MyApps;
import de.telekom.bmp.pages.account.Dashboard;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.Browser;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@QCId("5145")
@UseWebDriver
public class TC022_Invite_Colleague {

    private final String MAIL_PREFIX = "mybmptestuser";
    private final String PIERROT_STD_PWD = "galerien1?";
    private final String APP_DOMAIN = "testcloud.bmptest.de";
    private final String HTACCESS_CREDENTIALS="toon:HullyGully";

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

    @Inject
    GoogleMailAccount googleMailAccount;
    
    @Inject
    FunctionalActions fa;

    @Inject
    Browser browser;

    // the User who invites
    private User user;

    // the User to invite
    private User userToInvite;

    @BeforeTest
    public void setup() {
        // prepare User to invite;
        // also create a valid and not registered user
        userToInvite = User.createUser(MAIL_PREFIX);
        assertThat("User to invite is not null", userToInvite, notNullValue());

//change standard Default values (standard Password=12345!QAY)
        userToInvite.password = PIERROT_STD_PWD;
        userToInvite.name = "UserToInvite";
        userToInvite.firstName = "PierrotSQS";
//        datapool.save(userToInvite);

// Login with a normal User
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("mybmptestuser+normaluserwithapps@gmail.com").get();

        assertNotNull(user, "cannot find a valid user");

//        user.valid = false;
//        navigateTo(login);

    }

    @Test
    public void test_022_Invite_Colleague() throws InterruptedException {

        try {
            fa.login(user.email, user.password);           

 

            click(header.settingsMenu.accountLnk);

// WORKAROUND BECAUSE OF CMS
//            navigateTo(myApps);
//
//            assertThat(myApps, currentPage());
            String emailtoInvite = userToInvite.email;
            set(dashboardPage.inviteEmailInput, emailtoInvite);
            click(dashboardPage.singleInviteBtn);
            assertThat(dashboardPage.inviteSuccessfullTxt.isDisplayed());
            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());

            // wait the mail to synchronize
            Thread.sleep(5000);

            googleMailAccount.setMailAccount(emailtoInvite);
            String setInviteLink = googleMailAccount.checkGoogleMailAccountAndExtractConfirmLink(APP_DOMAIN, GoogleMailAccount.INVITE);
            setInviteLink = addHtaccessCredentials(setInviteLink);

            browser.navigate().to(setInviteLink);

            Thread.sleep(8000);

            // read email
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

    private String addHtaccessCredentials(String link) {
        return link.replaceFirst("//", "//" + HTACCESS_CREDENTIALS + "@");
    }

    private void createUserToSave() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

}
