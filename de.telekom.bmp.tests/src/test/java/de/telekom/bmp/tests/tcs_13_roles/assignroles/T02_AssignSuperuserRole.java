/**
 * 
 */
package de.telekom.bmp.tests.tcs_13_roles.assignroles;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.Assert.waitFor;
import static de.telekom.testframework.selenium.Matchers.displayed;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.concurrent.TimeUnit;

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
import de.telekom.bmp.pages.superuser.BillingAdminPage;
import de.telekom.bmp.pages.superuser.BillsPage;
import de.telekom.bmp.pages.superuser.DashboardPage;
import de.telekom.bmp.pages.superuser.ExceptionPage;
import de.telekom.bmp.pages.superuser.MarketplacesPage;
import de.telekom.bmp.pages.superuser.RebuildIndexPage;
import de.telekom.bmp.pages.superuser.ReportProcessPage;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.reporting.Reporter;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * @author Mathias Herkt
 * 
 */
@UseWebDriver
@QCId("5614")
public class T02_AssignSuperuserRole {
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
	DashboardPage suDashboardPg;

	@Inject
	ExceptionPage suExPg;
	
	@Inject
	BillingAdminPage suBillAdminPg;
	
	@Inject
	BillsPage suBillsPg;
	
	@Inject
	ReportProcessPage suRepProcPg;
	
	@Inject
	RebuildIndexPage suRebuildIdxPg;
	
	@Inject
	MarketplacesPage suMarketplacesPg;

	User superUser;
	User newSuUser;

	@BeforeMethod
	public void setup() {
		superUser = db.users().filter("role", UserRole.SUPERUSER)
				.filter("valid",  true).get();
		assertThat(superUser, is(not(nullValue())));
		
		newSuUser = db.users().filter("role", UserRole.USER)
				.field("name").contains("AutoSU").get();
		
		if (newSuUser == null) {
			Reporter.reportMessage("create new user for superuser");
			newSuUser = createNewUser();
		}
		
		assertThat(newSuUser, is(not(nullValue())));
	}

	@AfterMethod
	public void tearDown() {
	}

	@Test
	public void assignSuperuserRole() {
		fa.login(superUser);
		click(headerPage.settingsMenu.superuserLnk);
		click(suDashboardPg.userLnk);
		
		click(suDashboardPg.smallSearchInput);
		set(suDashboardPg.smallSearchInput, newSuUser.email);
		set(suDashboardPg.smallSearchInput, Keys.RETURN);
		waitFor(2, TimeUnit.SECONDS);
		assertThat(suDashboardPg.userLnk, is(displayed()));
		
		click(suDashboardPg.superUserChkBox);
		newSuUser.role = UserRole.SUPERUSER;
		db.save(newSuUser);
		fa.logout();
		waitFor(2, TimeUnit.SECONDS);
		checkSuperUserFeatures(newSuUser);
	}

	private void checkSuperUserFeatures(User user) {
		Reporter.reportMessage("checkSuperUserFeatures");
		fa.login(user);
		verifyThat(suDashboardPg, is(loaded()));
		click(headerPage.settingsMenu.superuserLnk);
		click(suDashboardPg.exceptionsTab);
		click(suExPg.billingAdminTab);
		click(suBillAdminPg.billsTab);
		click(suBillsPg.reportsTab);
		click(suRepProcPg.indexTab);
		click(suRebuildIdxPg.shopSettingsTab);
		fa.logout();
	}

	/**
	 * create a new user in bmp
	 * @return registered and valid user
	 */
	private User createNewUser() {
		User user = User.createUser(MAIL_PREFIX);	
		user.name += "+AutoSU";
		accHandling.registerAccount(user);
		db.save(user);
		
		return user;
	}
}
