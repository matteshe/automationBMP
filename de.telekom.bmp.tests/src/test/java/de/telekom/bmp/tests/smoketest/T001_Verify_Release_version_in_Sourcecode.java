/**
 * 
 */
package de.telekom.bmp.tests.smoketest;

import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import de.telekom.bmp.data.ApplicationInfo;
import de.telekom.bmp.data.Datapool;
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
	public static final String RELEASE_VERSION = "138";

	@Inject
	Home homePage;

	@Inject
	Datapool db;

	ApplicationInfo ai;

	@BeforeTest
	public void setup() {
		ai = db.appInfos().get();
		if (ai == null) {
			ai = new ApplicationInfo();
			ai.setVersion(RELEASE_VERSION);
			db.save(ai);
		}
	}

	@Test
	public void checkReleaseVersion() {
		navigateTo(homePage);
		assertThat("BMP Version: ", homePage.version.getValue(),
				equalTo(ai.getVersion()));
	}
}
