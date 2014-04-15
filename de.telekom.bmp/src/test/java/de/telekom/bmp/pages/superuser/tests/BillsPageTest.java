/**
 * 
 */
package de.telekom.bmp.pages.superuser.tests;

import de.telekom.bmp.pages.superuser.BillsPage;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.selenium.Matchers.*;
import static org.hamcrest.Matchers.*;

import javax.inject.Inject;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
public class BillsPageTest extends BasePageTest {
	@Inject
	BillsPage pg;
	
	@BeforeMethod
	public void setup() {
		login();
		Actions.navigateTo(pg);
		verifyThat(pg, is(loaded()));
	}
	
	@AfterMethod
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
