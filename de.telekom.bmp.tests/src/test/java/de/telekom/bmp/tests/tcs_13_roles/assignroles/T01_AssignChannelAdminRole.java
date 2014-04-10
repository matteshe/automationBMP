/**
 * 
 */
package de.telekom.bmp.tests.tcs_13_roles.assignroles;

import javax.inject.Inject;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.AccountHandling;
import de.telekom.bmp.functional.AssignRoles;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
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
	AssignRoles assignRoles;

	@Inject
	FunctionalActions fa;

	@Inject
	Header headerPage;

	User superUser;
	User userForChannelAdmin;

	@BeforeMethod
	public void setup() {
		superUser = db.users().filter("role", UserRole.SUPERUSER).get();
		Assert.assertThat("We need a super user for this test case.",
				superUser != null);

		userForChannelAdmin = db.users().filter("role", UserRole.USER)
				.filter("name", "AssignRole")
				.filter("firstName", "CHANNELADMIN").filter("valid", true)
				.get();

		if (userForChannelAdmin == null) {
			userForChannelAdmin = User.createUser(MAIL_PREFIX);
			userForChannelAdmin.name = "AssignRole";
			userForChannelAdmin.firstName = "CHANNELADMIN";
			accHandling.registerAccount(userForChannelAdmin);
			db.save(userForChannelAdmin);
			Assert.assertThat("New User registered",
					userForChannelAdmin.registered);
		} else {
			Reporter.reportMessage("Use registered user");
		}

	}

	@Test
	public void assignRole() {
		assignRoles.assignChannelAdmin(superUser, userForChannelAdmin);
		db.save(userForChannelAdmin);

		Reporter.reportMessage("Checks on new channel admin");
		fa.login(userForChannelAdmin.email, userForChannelAdmin.password);
		Actions.click(headerPage.settingsMenu.channelUserLnk);

		fa.logout();

	}
}
