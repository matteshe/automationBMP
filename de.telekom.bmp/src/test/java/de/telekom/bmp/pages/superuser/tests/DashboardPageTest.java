/**
 * 
 */
package de.telekom.bmp.pages.superuser.tests;

import de.telekom.bmp.pages.superuser.DashboardPage;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.selenium.Matchers.exists;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.telekom.bmp.pages.tests.BasePageTest;
import de.telekom.testframework.Actions;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * Test class to check locators on superuser billing admin page
 * @author Mathias Herkt
 *
 */
@UseWebDriver
public class DashboardPageTest extends BasePageTest {
	@Inject
	DashboardPage pg;
	
	@BeforeClass
	public void setup() {
		login();
		Actions.navigateTo(pg);
		verifyThat(pg, is(loaded()));
	}
	
	@AfterClass
	public void tearDown() {
		logout();
	}
	
	@Test
	public void verifySimpleLocators() {
		verifyThat(pg.overviewTab, exists());
		verifyThat(pg.exceptionsTab, exists());
		verifyThat(pg.reportsTab, exists());
		verifyThat(pg.billsTab, exists());
		verifyThat(pg.billingAdminTab, exists());
		verifyThat(pg.shopSettingsTab, exists());
		verifyThat(pg.indexTab, exists());
	}
}
