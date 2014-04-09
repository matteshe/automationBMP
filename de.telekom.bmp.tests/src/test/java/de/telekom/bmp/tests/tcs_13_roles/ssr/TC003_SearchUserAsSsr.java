/**
 * 
 */
package de.telekom.bmp.tests.tcs_13_roles.ssr;

import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.selenium.Matchers.exists;
import static org.hamcrest.Matchers.not;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.openqa.selenium.Keys;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.AccountHandling;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.channel.CompanyPage;
import de.telekom.bmp.pages.channel.MarketPlacePage;
import de.telekom.bmp.pages.channel.UserPage;
import de.telekom.testframework.Actions;
import de.telekom.testframework.Assert;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * @author Mathias Herkt
 * 
 */
@UseWebDriver
@QCId("5606")
public class TC003_SearchUserAsSsr {
	@Inject
	Browser browser;

	@Inject
	Datapool db;

	@Inject
	FunctionalActions fa;

	@Inject
	AccountHandling accHandling;

	@Inject
	Header hPage;

	@Inject
	MarketPlacePage mpPg;

	@Inject
	UserPage chUserPg;

	@Inject
	CompanyPage chCompPg;

	User ssrUser;
	User normalUser;

	@BeforeTest
	public void setup() {
		ssrUser = db.users().filter("role", UserRole.SSR)
				.filter("valid", true).get();
		Assert.assertThat("SSR user is available.", ssrUser != null);
		
		normalUser = db.users().filter("role", UserRole.USER)
				.filter("valid", true).get();
		Assert.assertThat("Normal user is available.", normalUser != null);
	}

	@AfterTest
	public void tearDown() {
		db.save(ssrUser);
	}

	@Test
	public void searchUserWithRoleSsr() {
		fa.login(ssrUser.email, ssrUser.password);
		Actions.click(hPage.settingsMenu.channelUserLnk);
		
		Assert.assertThat("search field visible.", mpPg.smallSearchInput.isDisplayed());
		
		User newUser = User.createUser("newUser");
		
		searchForUser(newUser);
		
		// TODO timeout is still 30 sec instead 0!!
		verifyThat(newUser.email + " can't be found.", mpPg.userInListLnk, not(exists()), 0);
		
		newUser.email = "";
		searchForUser(newUser);
		verifyThat("Empty string can't be found.", mpPg.userInListLnk, not(exists()), 0);
		
		newUser.email = "invalid@mail";
		searchForUser(newUser);
		verifyThat(newUser.email + " can't be found.", mpPg.userInListLnk, not(exists()), 0);
		
		searchForUser(normalUser);
		verifyThat(normalUser.email + " found.", mpPg.userInListLnk, exists(), 0);
		
		fa.logout();
	}
	
	private void searchForUser(User user) {
		mpPg.smallSearchInput.clear();
		Actions.click(mpPg.smallSearchInput);
		Actions.set(mpPg.smallSearchInput, user.email);
		mpPg.smallSearchInput.sendKeys(Keys.RETURN);
		Assert.waitFor(1, TimeUnit.SECONDS);
	}
}
