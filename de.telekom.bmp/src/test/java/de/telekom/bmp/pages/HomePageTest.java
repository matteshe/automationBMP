/**
 * 
 */
package de.telekom.bmp.pages;

import javax.inject.Inject;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.telekom.testframework.Actions;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.selenium.Matchers.*;
import static org.hamcrest.Matchers.*;

/**
 * This class tests the locators of the home page
 * @author Mathias Herkt
 *
 */
@UseWebDriver
public class HomePageTest extends BasePageTest {
	@Inject
	Home homePg;
	
	@BeforeMethod
	public void setup() {		
		Actions.navigateTo(homePg);
		verifyThat(homePg, is(loaded()));
	}
	
	@Test
	public void verifySimpleLocatorsWithoutLogin () {
		verifyThat(homePg.registerBtn, exists());
		verifyThat(homePg.version, exists());
		//verifyThat(homePg.feedbackMessage, exists());
	}
}
