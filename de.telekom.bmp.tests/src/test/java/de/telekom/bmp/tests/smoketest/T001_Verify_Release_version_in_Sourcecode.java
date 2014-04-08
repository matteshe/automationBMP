/**
 * 
 */
package de.telekom.bmp.tests.smoketest;

import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Assert.assertThat;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import de.telekom.bmp.pages.Home;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * @author Mathias Herkt
 * 
 */
@UseWebDriver
@QCId("5509")
public class T001_Verify_Release_version_in_Sourcecode {
	public static final String RELEASE_VERSION = "128.4";

	@Inject
	Home homePage;

	@BeforeTest
	public void setup() {
		navigateTo(homePage);
	}

	@Test
	public void checkReleaseVersion() {
		assertThat("version should be " + RELEASE_VERSION,
				RELEASE_VERSION.equals(homePage.version));
	}

}
