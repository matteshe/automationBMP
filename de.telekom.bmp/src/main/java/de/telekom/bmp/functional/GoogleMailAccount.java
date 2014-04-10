package de.telekom.bmp.functional;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.google.inject.Inject;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.MailAccount;
import de.telekom.bmp.pages.GoogleLoginPage;
import de.telekom.bmp.pages.GoogleReadMailPage;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.controls.Link;

/**
 * 
 * @author Mathias Herkt
 */
public class GoogleMailAccount {
	private static final String GOOGLE_MAIL_URL = "mail.google.com";
	private static final String MAIL_PASSWORD = "galerien3?";

	public static final String CONFIRM_MAIL = "Bitte bestätigen";
	public static final String PW_RESET = "Passwortzurücksetzung";
	public static final String INVITE = "eingeladen";

	@Inject
	Datapool db;

	@Inject
	Browser browser;

	@Inject
	GoogleLoginPage googlePage;

	@Inject
	GoogleReadMailPage readMailPage;

	MailAccount mailAccount;

	public void setMailAccount(String login) {
		String extractedMailAddress = extractEmailFromAlias(login);
		mailAccount = db.mailAccounts().field("mailAddress")
				.equal(extractedMailAddress).get();
		if (mailAccount == null) {
			mailAccount = new MailAccount();
			mailAccount.setProvider(GOOGLE_MAIL_URL);
			mailAccount.setMailAddress(extractedMailAddress);
			mailAccount.setPassword(MAIL_PASSWORD);
			db.save(mailAccount);
		}
	}

	public String checkGoogleMailAccountAndExtractConfirmLink(
			String linkDomain, String mailCategory) {
		browser.navigate().to(mailAccount.getProvider());

		loginIntoGoogleMailAccount();

		String confirmLink = readMailAndFindConfirmLink(linkDomain,
				mailCategory);

		logoutMailAccount();

		return confirmLink;
	}

	private void loginIntoGoogleMailAccount() {
		set(googlePage.email, mailAccount.getMailAddress());
		set(googlePage.password, mailAccount.getPassword());
		if (googlePage.stayLoggedIn.isSelected()) {
			click(googlePage.stayLoggedIn);
		}

		click(googlePage.loginBtn);
	}

	private void logoutMailAccount() {
		browser.navigate().to(googlePage.signoutLink.get("").getHref());
	}

	private String extractEmailFromAlias(final String emailAddress) {
		String newMailAddress = emailAddress;
		if (emailAddress.contains("+")) {
			String mailName = emailAddress.substring(0,
					emailAddress.indexOf("+"));
			String domainName = emailAddress.substring(emailAddress
					.indexOf("@"));
			newMailAddress = mailName + domainName;
		}

		return newMailAddress;
	}

	private String readMailAndFindConfirmLink(String linkDomain,
			String mailCategory) {
		Link confirmRegLnk = readMailPage.emailLnk.get(mailCategory);
		assertThat(confirmRegLnk, notNullValue());
		click(confirmRegLnk);

		String reallyConfirm = readMailPage.confirmLink.get(linkDomain)
				.getHref();
		assertThat(reallyConfirm, notNullValue());

		return reallyConfirm;
	}
}
