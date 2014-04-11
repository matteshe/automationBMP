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
@QCId("5607")
public class TC001_AssignSsrRoleViaSuperUser {
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
	User ssrUser;

	@BeforeMethod
	public void setup() {
		superUser = db.users().filter("role", UserRole.SUPERUSER)
				.filter("valid", true).get();
		Assert.assertThat("super useris available", superUser != null);

		ssrUser = db.users().filter("role", UserRole.USER)
				.filter("valid", true).field("name").contains("SET SSR").get();

		if (ssrUser == null) {
			Reporter.reportMessage("Create a new user");
			ssrUser = User.createUser(MAIL_PREFIX);
			ssrUser.name += "+SET SSR";
			accHandling.registerAccount(ssrUser);
			db.save(ssrUser);
		} else {
			Reporter.reportMessage("Use a registered user");
		}
	}

	@AfterMethod
	public void tearDown() {
		db.save(ssrUser);
	}

	@Test
	public void assignSsrRole() {
		assignRoles.assignSsrRoleViaSuperUser(superUser, ssrUser);
		
		// TODO no verify steps in test case script!
	}
}
