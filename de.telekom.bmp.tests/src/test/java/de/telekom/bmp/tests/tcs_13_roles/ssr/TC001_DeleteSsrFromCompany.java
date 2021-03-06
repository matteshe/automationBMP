/**
 * 
 */
package de.telekom.bmp.tests.tcs_13_roles.ssr;

import static de.telekom.testframework.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import javax.inject.Inject;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.AssignRoles;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * @author Mathias Herkt
 * 
 */
@UseWebDriver
@QCId("5602")
public class TC001_DeleteSsrFromCompany {
	@Inject
	Datapool db;

	@Inject
	AssignRoles assignRoles;

	User channelAdminUser;
	User ssrUser;

	@BeforeMethod
	public void setup() {
		channelAdminUser = db.users().filter("role", UserRole.CHANNELADMIN)
				.filter("valid", true).get();
		assertThat(channelAdminUser, is(not(nullValue())));

		ssrUser = db.users().filter("role", UserRole.RSSR)
				.filter("valid", true).field("name").contains("SET RSSR").get();

		assertThat(ssrUser, is(not(nullValue())));
	}

	@AfterMethod
	public void tearDown() {
		db.save(ssrUser);
	}

	@Test
	public void deleteSsrRole() {
		assignRoles.removeSsrRole(channelAdminUser, ssrUser);
		assertThat(ssrUser.role, is(UserRole.USER));
	}
}
