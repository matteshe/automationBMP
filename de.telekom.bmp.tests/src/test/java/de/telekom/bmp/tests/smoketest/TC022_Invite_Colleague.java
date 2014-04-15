package de.telekom.bmp.tests.smoketest;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static de.telekom.testframework.selenium.Matchers.displayed;
import static de.telekom.testframework.selenium.Matchers.exists;
import static org.hamcrest.Matchers.is;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.AccountHandling;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.account.Dashboard;
import de.telekom.bmp.pages.account.Users;
import de.telekom.testframework.Actions;
import de.telekom.testframework.Assert;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.reporting.Reporter;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

@QCId("5145")
@UseWebDriver
public class TC022_Invite_Colleague {

    private static final String MAIL_PREFIX = "mybmptestuser";

    @Inject
    BmpApplication app;

    @Inject
    FunctionalActions fa;

    @Inject
    Users acctUserPg;

    @Inject
    Header header;

    @Inject
    Dashboard dashboardPage;

    @Inject
    Datapool datapool;

    @Inject
    AccountHandling accHandling;

    User normalUser;

    @BeforeMethod
    public void setup() {
        normalUser = datapool.users()
                .filter("role", UserRole.USER)
                .filter("valid", true)
                .field("name").contains("ForInvite").get();
        if (normalUser == null) {
            Reporter.reportMessage("Create new user for invitation");
            normalUser = User.createUser(MAIL_PREFIX);
            normalUser.name = "+ForInvite";
            accHandling.registerAccount(normalUser);
            datapool.save(normalUser);
        }
        Actions.navigateTo(app);
    }

    @Test
    public void inviteColleague() {

        // 1 call invite on action handling
        User invitedUser = getInviteUser();
        accHandling.inviteUser(normalUser, invitedUser);
        // user registered
        datapool.save(invitedUser);

        // 2 check if new user is invited correctly
        fa.login(normalUser.email, normalUser.password);

        click(header.settingsMenu.accountLnk);
        assertThat(dashboardPage.usersLnk, is(displayed()));
        click(dashboardPage.usersLnk);
        click(acctUserPg.searchInput);
        set(acctUserPg.searchInput, invitedUser.name);
        acctUserPg.searchInput.sendKeys(Keys.RETURN);
        Actions.waitFor(2, TimeUnit.SECONDS);
        Assert.verifyThat("new invited user is shown in list", acctUserPg.userInListLnk, exists(), 0);

        fa.logout();
    }

    /**
     * Try to find a user to invite in test db or creates a new one
     *
     * @return the user to invite
     */
    private User getInviteUser() {
        User inviteUser = null;
        Reporter.reportMessage("Create new user for invatation.");
        inviteUser = User.createUser(MAIL_PREFIX);
        inviteUser.name += "+INVITE";
        inviteUser.valid = true;
        datapool.save(inviteUser);

        return inviteUser;
    }
}
