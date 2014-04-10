package de.telekom.bmp.tests.smoketest;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static de.telekom.testframework.selenium.Matchers.exists;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import static org.testng.Assert.assertNotNull;

import java.util.Date;
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

    //@Test
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
    
    //@Test
    public void justLogin() {
    	User normalUser = datapool.users().filter("role", UserRole.USER).filter("valid",true).get();
    	assertThat("normal user exists.", normalUser != null);
    	
    	assertThat("user logged out", login.signinBtn.isDisplayed());
    	fa.login(normalUser.email, normalUser.password);
    	Assert.waitFor(2, TimeUnit.SECONDS);
    	fa.logout();
    	assertThat("user logged out", login.signinBtn.isDisplayed());
    	Assert.waitFor(5, TimeUnit.SECONDS);
    }
    
    @Test
    public void inviteColleague() {
    	User normalUser = datapool.users().filter("role", UserRole.USER).filter("valid",true).get();
    	assertThat("normal user exists.", normalUser != null);
    	
    	assertThat("user logged out", login.signinBtn.isDisplayed());
    	fa.login(normalUser.email, normalUser.password);

    	click(header.settingsMenu.accountLnk);
    	
    	User inviteUser = getInviteUser();
    	    	
    	set(dashboardPage.inviteEmailInput, inviteUser.email);
        click(dashboardPage.singleInviteBtn);
        assertThat(dashboardPage.inviteSuccessfullTxt.isDisplayed());
        
        Actions.navigateTo(home);
        Assert.waitFor(5, TimeUnit.SECONDS);
        fa.logout();
        assertThat("user logged out", header.loginBtn.isDisplayed());
        
        
        gmail.setMailAccount(inviteUser.email);
        String confirmLink = gmail.checkGoogleMailAccountAndExtractConfirmLink(AccountHandling.APP_DOMAIN, GoogleMailAccount.INVITE);
        assertThat("confirm link is not empty", !"".equals(confirmLink));
        confirmLink = addHtaccessCredentials(confirmLink);
        
        browser.navigate().to(confirmLink);
        Actions.waitFor(2, TimeUnit.SECONDS);
        
        setupAccountInformations(inviteUser);
        //Assert.verifyThat("invited user is logged in", header.accountMenu.logoutLnk.isDisplayed());
        
        fa.logout();
        assertThat("user logged out", header.loginBtn.isDisplayed());
        
        // check new user
        fa.login(normalUser.email, normalUser.password);
        click(header.settingsMenu.accountLnk);
        Actions.waitFor(5, TimeUnit.SECONDS);
        click(dashboardPage.usersLnk);
        click(acctUserPg.searchInput);
        set(acctUserPg.searchInput, inviteUser.name);
        acctUserPg.searchInput.sendKeys(Keys.RETURN);
        Actions.waitFor(2, TimeUnit.SECONDS);
        Assert.verifyThat("new invited user is shown in list", acctUserPg.userInListLnk, exists(), 0);

        fa.logout();
    }

    private void setupAccountInformations(User user) {
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
    	User inviteUser = null;// = datapool.users().filter("registered", false).filter("valid", true).get();
    	
    	if (inviteUser == null) {
    		Reporter.reportMessage("Create new user for invatation.");
    		inviteUser = User.createUser(MAIL_PREFIX);
        	inviteUser.name += "+INVITE";
        	inviteUser.valid = true;
        	datapool.save(inviteUser);
    	} else {
    		Reporter.reportMessage("Use non registered user for invatation.");
    	}
    	
    	return inviteUser;
    }

}
