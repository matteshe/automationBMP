package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.EMailAccount;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Invitations;
import de.telekom.bmp.pages.account.Account;
import de.telekom.bmp.pages.account.Dashboard;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

@QCId(value = "5145", state = QCState.ReadyButNeedsReview)
@UseWebDriver
public class TC022_Invite_Colleague {

    @Inject
    FunctionalActions fa;

    @Inject
    Home home;

    @Inject
    Header header;

    @Inject
    Account account;

    @Inject
    Dashboard dashboard;

    @Inject
    Invitations invitations;

    @Inject
    Datapool datapool;

    User invitingUser;
    User invitedUser;

    @Test
    public void setup() {
        invitingUser = datapool.validUsers()
                .field(User.Fields.registered).equal(true).get();

        invitedUser = datapool.helpers().defineNewTestUser("invited");
    }

    @Test(dependsOnMethods = "setup")
    public void loginInvitingUser() {
        assertThat("we have an inviting user", invitingUser != null && invitingUser.valid);
        assertThat("we have an invited user", invitedUser != null && invitedUser.valid && !invitedUser.invited);

        fa.login(invitingUser);
    }

    @Test(dependsOnMethods = "loginInvitingUser")
    public void inviteColleague() {
        invitedUser.valid = false;
        invitedUser.invited = false;

        click(header.settings.account);

        click(account.tabs.dashboard);

        set(dashboard.inviteColleages.email, invitedUser.email);
        click(dashboard.inviteColleages.submit);

        verifyThat(dashboard.inviteColleages.info, exists());
        verifyThat(dashboard.inviteColleages.error, not(exists()));

        invitedUser.invited = true;
        invitedUser.valid = true;

        datapool.save(invitedUser);
    }

    @Test(dependsOnMethods = "inviteColleague")
    public void logoutInvitingUser() {
        fa.logout();
    }

    @Test(dependsOnMethods = "logoutInvitingUser")
    public void confirmEmail() {
        assertThat("we have an invited user", invitedUser != null && invitedUser.valid && invitedUser.invited);
        invitedUser.valid = false;

        String link = EMailAccount.getInvitationsLink(invitedUser);

        assertThat("we have a confirmation link", link, not(isEmptyOrNullString()));
        assertThat("the confirmation link is a valid url", link, is(url()));

        navigateTo(link);

        assertThat(invitations, is(currentPage()));

        set(invitations.firstName, invitedUser.firstName);
        set(invitations.lastName, invitedUser.name);
        set(invitations.password, invitedUser.password);
        set(invitations.confirmPassword, invitedUser.password);

        click(invitations.signup);

        assertThat(home, is(currentPage()));

        invitedUser.registered = true;
        invitedUser.valid = true;
    }

    @Test(dependsOnMethods = "confirmEmail")
    public void logoutInvitedUser() {
        datapool.save(invitedUser);
        // TODO some checks, but none defined in HP ALM
        fa.logout();
    }

    @Test(dependsOnMethods = "logoutInvitedUser")
    public void loginAndOutTheInvitedUser() {
        fa.login(invitedUser);
        // TODO some checks, but none defined in HP ALM
        fa.logout();
    }

}
