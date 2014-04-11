/**
 * 
 */
package de.telekom.bmp.tests.tcs_13_roles.ssr;

import javax.inject.Inject;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.AccountHandling;
import de.telekom.bmp.functional.AssignRoles;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
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
	AssignRoles assignRoles;

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
		assignRoles.assignRssrRoleViaSuperUser(superUser, rssrUser);
		
		// TODO no verify steps in test case script available!
	}
}
