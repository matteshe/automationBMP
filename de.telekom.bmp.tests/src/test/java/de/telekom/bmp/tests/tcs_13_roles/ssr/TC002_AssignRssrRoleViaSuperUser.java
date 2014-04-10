/**
 * 
 */
package de.telekom.bmp.tests.tcs_13_roles.ssr;

import javax.inject.Inject;

import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
import de.telekom.testframework.reporting.Reporter;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * @author Mathias Herkt
 * 
 */
@UseWebDriver
@QCId("5597")
public class TC002_AssignRssrRoleViaSuperUser {
	private static final String MAIL_PREFIX = "mybmptestuser";

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

	User superUser;
	User rssrUser;

	@BeforeMethod
	public void setup() {
		superUser = db.users().filter("role", UserRole.SUPERUSER)
				.filter("valid", true).get();
		Assert.assertThat("super useris available", superUser != null);

		rssrUser = db.users().filter("role", UserRole.USER)
				.filter("valid", true).field("name").contains("SET RSSR").get();

		if (rssrUser == null) {
			Reporter.reportMessage("Create a new user");
			rssrUser = User.createUser(MAIL_PREFIX);
			rssrUser.name += "+SET RSSR";
			accHandling.registerAccount(rssrUser);
			db.save(rssrUser);
		} else {
			Reporter.reportMessage("Use a registered user");
		}
	}

	@AfterMethod
	public void tearDown() {
		db.save(rssrUser);
	}

	@Test
	public void assignRssrRole() {
		fa.login(superUser.email, superUser.password);
		Actions.click(hPage.settingsMenu.channelUserLnk);
		Actions.click(mpPg.customerLnk);

		Assert.assertThat("Search field visible.", mpPg.smallSearchInput.isDisplayed());
		Actions.click(mpPg.smallSearchInput);
		Actions.set(mpPg.smallSearchInput, rssrUser.email);
		mpPg.smallSearchInput.sendKeys(Keys.RETURN);
		waitForMilliSec(1000);
		
		// choose user from list
		mpPg.userInListLnk.click();
		waitForMilliSec(500);
		Assert.assertThat("link for customer data is visible",
				chUserPg.customerDataLnk.isDisplayed());
		Actions.click(chUserPg.customerDataLnk);

		Assert.assertThat("channel admin checkbox is visible.",
				chCompPg.channelAdminChkbox.isDisplayed());
		Assert.assertThat("channel admin checkbox is not checked.",
				!chCompPg.channelAdminChkbox.isSelected());

		chCompPg.channelAdminChkbox.select();
		Assert.assertThat("feedback company is channel admin is displayed.",
				chCompPg.feedbackPanelINFO.isDisplayed());
		
		Actions.click(chCompPg.userLnk.get(rssrUser.email));
		Assert.assertThat("restricted sales support checkbox is visible.",
				chUserPg.restrictedSalesSupportChkbox.isDisplayed());
		chUserPg.restrictedSalesSupportChkbox.select();
		Assert.assertThat("feedback company is channel admin is displayed.",
				chUserPg.feedbackPanelINFO.isDisplayed());
		
		rssrUser.role = UserRole.RSSR;
		fa.logout();
	}

	private void waitForMilliSec(long value) {
		try {
			Thread.sleep(value);
		} catch (InterruptedException e) {
			// do nothing
		}
	}
}
