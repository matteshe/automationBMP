package de.telekom.bmp.tests.smoketest;

import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.AccountHandling;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.functional.GoogleMailAccount;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.InvitationPage;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MyApps;
import de.telekom.bmp.pages.account.Dashboard;
import de.telekom.bmp.pages.account.Users;
import de.telekom.testframework.Actions;
import de.telekom.testframework.Assert;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.reporting.Reporter;
import de.telekom.testframework.selenium.Browser;
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
    GoogleMailAccount gmail;

    @Inject
    Browser browser;
    
    @Inject
    InvitationPage invitePage;
    
    @Inject
    Users acctUserPg;
    
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
    AccountHandling accHandling;
    
    User normalUser;
    
    @BeforeTest
    public void setup() {
    	normalUser = datapool.users()
    			.filter("role", UserRole.USER)
    			.filter("valid",true)
    			.field("name").contains("normalUserForInvite").get();
    	if (normalUser == null) {
    		Reporter.reportMessage("Create new user for invitation");
    		normalUser = User.createUser(MAIL_PREFIX);
    		normalUser.name = "+normalUserForInvite";
    		accHandling.registerAccount(normalUser);
    		datapool.save(normalUser);
    	}
        navigateTo(login);
    }
    
    @Test
    public void inviteColleague() {
    	assertThat(normalUser, is(not(nullValue())));
    	
    	assertThat(login.signinBtn, is(displayed()));
    	fa.login(normalUser.email, normalUser.password);
    	click(header.settingsMenu.accountLnk);
    	
    	User inviteUser = getInviteUser();
    	    	
    	set(dashboardPage.inviteEmailInput, inviteUser.email);
        click(dashboardPage.singleInviteBtn);
        assertThat(dashboardPage.inviteSuccessfullTxt, is(displayed()));
        
        Actions.navigateTo(home);
        fa.logout();
        assertThat(header.loginBtn, is(displayed()));        
        
        gmail.setMailAccount(inviteUser.email);
        String confirmLink = gmail.checkGoogleMailAccountAndExtractConfirmLink(AccountHandling.APP_DOMAIN, normalUser.name);
        assertThat("confirm link is not empty", !"".equals(confirmLink));
        confirmLink = addHtaccessCredentials(confirmLink);
        
        browser.navigate().to(confirmLink);
        setupAccountInformations(inviteUser);
        
        fa.logout();
        assertThat(header.loginBtn, is(displayed()));
        
        // check new user
        fa.login(normalUser.email, normalUser.password);
        click(header.settingsMenu.accountLnk);
        assertThat(dashboardPage.usersLnk, is(displayed()));
        click(dashboardPage.usersLnk);
        click(acctUserPg.searchInput);
        set(acctUserPg.searchInput, inviteUser.name);
        acctUserPg.searchInput.sendKeys(Keys.RETURN);
        Actions.waitFor(2, TimeUnit.SECONDS);
        Assert.verifyThat("new invited user is shown in list", acctUserPg.userInListLnk, exists(), 0);

        fa.logout();
    }

    private void setupAccountInformations(User user) {
    	assertThat(invitePage.firstNameInput, is(displayed()));
		set(invitePage.firstNameInput, user.firstName);
		set(invitePage.lastNameInput, user.name);
		set(invitePage.passwordInput, user.password);
		set(invitePage.confirmPasswordInput, user.password);
		click(invitePage.submit);
	}

	/**
	 * Add htaccess credentials to given link
	 * 
	 * @param link
	 *            which should be changed
	 * @return a link with htaccess cred
	 */
	private String addHtaccessCredentials(String link) {
		return link.replaceFirst("//", "//" + AccountHandling.HTACCESS_CREDENTIALS + "@");
	}
    
    /**
     * Try to find a user to invite in test db or creates a new one
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
