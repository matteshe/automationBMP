/**
 * 
 */
package de.telekom.bmp.tests.tcs_13_roles.ssr;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import de.telekom.testframework.Assert;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * @author htm
 * 
 */
@UseWebDriver
@QCId("5597")
public class TC001_AssignSsrRoleViaChannelAdmin {

	@BeforeTest
	public void setup() {

	}

	@AfterTest
	public void tearDown() {

	}

	@Test
	public void assignSrrRole() {
		Assert.assertThat(false);
	}
}
