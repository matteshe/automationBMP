package de.telekom.bmp.functional;

import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.*;
import static org.hamcrest.Matchers.*;

import javax.inject.Inject;

import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Invitations;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.Signup;
import de.telekom.bmp.pages.account.Dashboard;
import de.telekom.bmp.pages.accountsetup.AccountActivationPage;
import de.telekom.testframework.selenium.Browser;

/**
 * @author Mathias Herkt
 *
 */
@Deprecated
public class AccountHandling {

    public static final String APP_DOMAIN = "testcloud.bmptest.de";

    public static final String HTACCESS_CREDENTIALS = "toon:HullyGully";

    @Inject
    Signup signup;

    @Inject
    Home homePage;

    @Inject
    Browser browser;

    @Inject
    Login login;

    @Inject
    Header header;

    @Inject
    Dashboard dashboardPage;

    @Inject
    Home home;

    @Inject
    Invitations invitePage;

    @Inject
    AccountActivationPage accountActivation;

    @Inject
    GoogleMailAccount gmail;

    @Inject
    FunctionalActions fa;

    /**
     * Method to register a new user based on the given mail address
     *
     * @param user
     */
    public void registerAccount(User user) {
        navigateTo(signup);

        click(homePage.registerBtn);
        set(signup.signupForm.emailAddress, user.email);

        assertThat(signup.signupForm.iconValid, is(displayed()));

        // button to register
        click(signup.signupForm.signup);

        // read email
        String confirmLink = getConfirmLink(user.email,
                GoogleMailAccount.CONFIRM_MAIL);
        browser.navigate().to(confirmLink);

        setupAccountDetails(user);
        user.registered = true;
        user.valid = true;
        fa.logout();
    }

    public String getConfirmLink(String mailAddress) {
        return getConfirmLink(mailAddress, GoogleMailAccount.CONFIRM_MAIL);
    }

    /**
     * Method tries to read an email to receive a confirm link
     *
     * @param mailAddress of the gmail account
     * @param mailCategory category for mail
     * @return a link to confirm
     */
    public String getConfirmLink(String mailAddress, String mailCategory) {
        //newWindow();
        try {
            gmail.setMailAccount(mailAddress);

            String confirmLink = gmail.checkGoogleMailAccountAndExtractConfirmLink(
                    APP_DOMAIN, mailCategory);
            assertThat(confirmLink, !confirmLink.equals(""));
            return addHtaccessCredentials(confirmLink);
        } finally {
            //closeWindow();
        }
    }

    /**
     * Add htaccess credentials to given link
     *
     * @param link which should be changed
     * @return a link with htaccess cred
     */
    private String addHtaccessCredentials(String link) {
        return link.replaceFirst("//", "//" + HTACCESS_CREDENTIALS + "@");
    }

    /**
     * Method to setup the account details of a given user
     *
     * @param user with detailed information
     */
    private void setupAccountDetails(User user) {
        set(accountActivation.firstName, user.firstName);
        set(accountActivation.lastName, user.name);
        set(accountActivation.companyName, user.companyName);
        set(accountActivation.password, user.password);
        set(accountActivation.confirmPassword, user.password);
        click(accountActivation.termsAndCondition);
        click(accountActivation.createAccountBtn);
    }

    /**
     * Invite a user
     *
     * @param user which will invite another user
     * @param invitedUser the user to be invited
     */
    public void inviteUser(User user, User invitedUser) {
//        assertThat(user, is(not(nullValue())));
//        assertThat(invitedUser, is(not(nullValue())));
//        verifyThat(header.login, is(displayed()));
//
//        fa.login(user.email, user.password);
//        click(header.settings.account);
//
//        set(dashboardPage.inviteEmailInput, invitedUser.email);
//        click(dashboardPage.singleInviteBtn);
//        assertThat(dashboardPage.inviteSuccessfullTxt, is(displayed()));
//
//        Actions.navigateTo(home);
//        fa.logout();
//        assertThat(header.login, is(displayed()));
//
//        gmail.setMailAccount(invitedUser.email);
//        String confirmLink = gmail.checkGoogleMailAccountAndExtractConfirmLink(APP_DOMAIN, user.name);
//        assertThat("confirm link is not empty", !"".equals(confirmLink));
//        confirmLink = addHtaccessCredentials(confirmLink);
//
//        browser.navigate().to(confirmLink);
//        setupAccountInformations(invitedUser);
//
//        fa.logout();
//        assertThat(header.login, is(displayed()));
    }

    private void setupAccountInformations(User user) {
        assertThat(invitePage.firstName, is(displayed()));
        set(invitePage.firstName, user.firstName);
        set(invitePage.lastName, user.name);
        set(invitePage.password, user.password);
        set(invitePage.confirmPassword, user.password);
        click(invitePage.signup);
        user.registered = true;
    }
}
