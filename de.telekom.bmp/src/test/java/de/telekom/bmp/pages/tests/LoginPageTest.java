/**
 * 
 */
package de.telekom.bmp.pages.tests;

import de.telekom.bmp.pages.Login;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static org.hamcrest.Matchers.*;
import static de.telekom.testframework.selenium.Matchers.*;

import javax.inject.Inject;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.telekom.testframework.Actions;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * Test class to check locators on login page
 * @author Mathias Herkt
 *
 */
@UseWebDriver
public class LoginPageTest extends BasePageTest {
	@Inject
	Login pg;
	
	@BeforeMethod
	public void setup() {		
		Actions.navigateTo(pg);
		verifyThat(pg, is(loaded()));
	}
	
	@Test
	public void verifySimpleLocators() {
		verifyThat(pg.usernameInput, exists());
		verifyThat(pg.passwordInput, exists());
		verifyThat(pg.signinBtn, exists());
		verifyThat(pg.signuplink, exists());
		verifyThat(pg.forgotPasswordLnk, exists());
	}
}
