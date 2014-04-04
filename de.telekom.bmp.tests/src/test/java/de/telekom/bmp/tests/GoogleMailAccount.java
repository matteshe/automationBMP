package de.telekom.bmp.tests;

import com.google.inject.Inject;
import de.telekom.bmp.pages.GoogleLoginPage;
import de.telekom.bmp.pages.GoogleReadMailPage;
import static de.telekom.bmp.tests.smoketest.TC002_RegistrationWithValidCredentials.APP_DOMAIN;
import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.controls.Link;
import static org.hamcrest.Matchers.notNullValue;

/**
 *
 * @author Mathias Herkt
 */
@UseWebDriver
public class GoogleMailAccount {
    public static final String GOOGLE_MAIL_URL = "mail.google.com";
    public static final String MAIL_PASSWORD = "galerien3?";
    
    private final String mail;
    private final String password;
    

    private Browser browser;
    private GoogleLoginPage googlePage;
    private GoogleReadMailPage readMailPage;
    
    public GoogleMailAccount(String login, String pw) {
        this.mail = login;
        this.password = pw;
    }
    
    public GoogleMailAccount browser(Browser browser) {
        this.browser = browser;
        return this;
    }
    
    public GoogleMailAccount loginPage(GoogleLoginPage page) {
        this.googlePage = page;
        return this;
    }
    
    public GoogleMailAccount readMailPage(GoogleReadMailPage page) {
        this.readMailPage = page;
        return this;
    }
    
    public String checkGoogleMailAccountAndExtractConfirmLink() {
        browser.navigate().to(GOOGLE_MAIL_URL);
        
        loginIntoGoogleMailAccount();

        return readMailAndFindConfirmLink();   
    }
    
    private void loginIntoGoogleMailAccount() {
        set(googlePage.email, extractEmailFromAlias(this.mail));
        set(googlePage.password, this.password);
        click(googlePage.loginBtn);
    }
    private String extractEmailFromAlias(final String emailAddress) {
        String newMailAddress = emailAddress;
        if (emailAddress.contains("+")) {
            String mailName = emailAddress.substring(0, emailAddress.indexOf("+"));
            String domainName = emailAddress.substring(emailAddress.indexOf("@"));
            newMailAddress = mailName + domainName;
        }
        
        return newMailAddress;
    }
    
    private String readMailAndFindConfirmLink() {
        Link confirmRegLnk = readMailPage.emailLinks.get(0);
        assertThat(confirmRegLnk, notNullValue());
        click(confirmRegLnk);
        
        String reallyConfirm = readMailPage.confirmLink.get(APP_DOMAIN).getHref();
        assertThat(reallyConfirm, notNullValue());
        
        return reallyConfirm;
    }
}
