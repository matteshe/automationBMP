/**
 * 
 */
package de.telekom.bmp.tests.tcs_13_roles.assignroles;

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
import de.telekom.bmp.pages.superuser.DashboardPage;
import de.telekom.testframework.Actions;
import de.telekom.testframework.Assert;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.reporting.Reporter;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * @author Mathias Herkt
 * 
 */
@UseWebDriver
@QCId("5612")
public class T01_AssignChannelAdminRole {
	private static final String MAIL_PREFIX = "mybmptestuser";

	@Inject
	Datapool db;

	@Inject
	AccountHandling accHandling;

	@Inject
	FunctionalActions fa;

	@Inject
	Header headerPage;

	@Inject
	DashboardPage dashboardPage;

	User superUser;
	User userForChannelAdmin;

	@BeforeTest
	public void setup() {
		superUser = db.users().filter("role", UserRole.SUPERUSER).get();
		Assert.assertThat("We need a super user for this test case.",
				superUser != null);

		userForChannelAdmin = db.users().filter("role", UserRole.CHANNELADMIN)
				.filter("name", "AssignRole")
				.filter("firstName", "CHANNELADMIN").filter("valid", true)
				.get();

		if (userForChannelAdmin == null) {
			userForChannelAdmin = User.createUser(MAIL_PREFIX);
			userForChannelAdmin.name = "AssignRole";
			userForChannelAdmin.firstName = "CHANNELADMIN";
			userForChannelAdmin.role = UserRole.CHANNELADMIN;
			accHandling.registerAccount(userForChannelAdmin);
			db.save(userForChannelAdmin);
			Assert.assertThat("New User registered",
					userForChannelAdmin.registered);
		} else {
			Reporter.reportMessage("Use registered user");
		}

	}

	@AfterTest
	public void tearDown() {

	}

	@Test
	public void assignRole() {
		fa.login(superUser.email, superUser.password);
		Actions.click(headerPage.settingsMenu.superuserLnk);

		Actions.click(dashboardPage.companyLnk);
		Assert.assertThat("small search is displayed.",
				dashboardPage.smallSearchInput.isDisplayed());

		Actions.click(dashboardPage.smallSearchInput);
		Actions.set(dashboardPage.smallSearchInput, userForChannelAdmin.company);
		Reporter.reportMessage("Send Return to small search text field");
		dashboardPage.smallSearchInput.sendKeys(Keys.RETURN);
		Assert.assertThat("channel admin checkbox is not checked.",
				!dashboardPage.channelAdminChkBox.isSelected());
		Actions.click(dashboardPage.channelAdminChkBox);

		// if the test come here, the account isn't valid anymore
		userForChannelAdmin.valid = false;
		db.save(userForChannelAdmin);

		Actions.click(dashboardPage.userLnk);

		Actions.click(dashboardPage.smallSearchInput);
		Actions.set(dashboardPage.smallSearchInput, userForChannelAdmin.email);
		Reporter.reportMessage("Send Return to small search text field");
		dashboardPage.smallSearchInput.sendKeys(Keys.RETURN);
		Assert.assertThat("user channel admin checkbox is displayed.",
				dashboardPage.userChannelAdminChkBox.isDisplayed());
		Assert.assertThat("user channel admin checkbox is not checked.",
				!dashboardPage.userChannelAdminChkBox.isSelected());
		Actions.click(dashboardPage.userChannelAdminChkBox);

		fa.logout();

		Reporter.reportMessage("Login as new channel admin");
		fa.login(userForChannelAdmin.email, userForChannelAdmin.password);
		Actions.click(headerPage.settingsMenu.channelUserLnk);

		fa.logout();

	}
}
