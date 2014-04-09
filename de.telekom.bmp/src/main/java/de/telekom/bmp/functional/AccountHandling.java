/**
 * 
 */
package de.telekom.bmp.functional;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;

import javax.inject.Inject;

import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Signup;
import de.telekom.bmp.pages.accountsetup.AccountActivationPage;
import de.telekom.testframework.selenium.Browser;

/**
 * @author Mathias Herkt
 * 
 */
public class AccountHandling {
	private static final String APP_DOMAIN = "testcloud.bmptest.de";

	private static final String HTACCESS_CREDENTIALS = "toon:HullyGully";

	@Inject
	Signup signup;

	@Inject
	Home homePage;

	@Inject
	Browser browser;

	@Inject
	AccountActivationPage accountActivation;

	@Inject
	GoogleMailAccount gmail;

	@Inject
	FunctionalActions fa;

	/**
	 * Method to register a new user based on the given mail address
	 * 
	 * @param emailAddress
	 *            of the new user
	 * @throws InterruptedException
	 *             if something goes wrong with sleep
	 */
	public void registerAccount(User user) throws InterruptedException {
		navigateTo(signup);

		click(homePage.registerBtn);

		set(signup.emailAddress, user.email);

		assertThat("signup.iconValid.isDisplayed",
				signup.iconValid.isDisplayed());

		Thread.sleep(500);

		// button to register
		click(signup.signup);

		// read email
		String confirmLink = getConfirmLink(user.email,
				GoogleMailAccount.CONFIRM_MAIL);
		browser.navigate().to(confirmLink);

		setupAccountDetails(user);
		user.registered = true;
		user.valid = true;
		fa.logout();
	}

	/**
	 * Method tries to read an email to receive a confirm link
	 * 
	 * @param mailAddress
	 *            of the gmail account
	 * @param mailCategory
	 *            category for mail
	 * @return a link to confirm
	 * @throws InterruptedException
	 */
	private String getConfirmLink(String mailAddress, String mailCategory)
			throws InterruptedException {
		gmail.setMailAccount(mailAddress);

		String confirmLink = gmail.checkGoogleMailAccountAndExtractConfirmLink(
				APP_DOMAIN, mailCategory);
		assertThat(confirmLink, !confirmLink.equals(""));
		return addHtaccessCredentials(confirmLink);
	}

	/**
	 * Add htaccess credentials to given link
	 * 
	 * @param link
	 *            which should be changed
	 * @return a link with htaccess cred
	 */
	private String addHtaccessCredentials(String link) {
		return link.replaceFirst("//", "//" + HTACCESS_CREDENTIALS + "@");
	}

	/**
	 * Method to setup the account details of a given user
	 * 
	 * @param user
	 *            with detailed information
	 */
	private void setupAccountDetails(User user) {
		set(accountActivation.firstName, user.firstName);
		set(accountActivation.lastName, user.name);
		set(accountActivation.companyName, user.companyName);
		set(accountActivation.password, user.password);
		set(accountActivation.confirmPassword, user.password);
		click(accountActivation.termsAndCondition);
		click(accountActivation.createAccountBtn);
	}
}
