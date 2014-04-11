/**
 * 
 */
package de.telekom.bmp.pages;

import static de.telekom.testframework.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import javax.inject.Inject;

import org.testng.annotations.BeforeClass;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.testframework.Actions;
import de.telekom.testframework.reporting.Reporter;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * This is the base class to simply test the page objects. It will provide the
 * login into the app with a super user
 * 
 * @author Mathias Herkt
 */
@UseWebDriver
public class BasePageTest {

	@Inject
	Datapool db;
	
	@Inject
	protected BmpApplication app;
	
	@Inject
	protected FunctionalActions fa;
	
	protected User user;
	
	@BeforeClass
	public void setupUser() {
		Reporter.reportMessage("login");
		user = db.users().filter("role", UserRole.SUPERUSER).filter("valid", true).get();
		assertThat(user, is(not(nullValue())));
		
		Actions.navigateTo(app);
	}
	
	protected void login() {
		fa.login(user);
	}
	
	protected void logout() {
		fa.logout();
	}
}
