package de.telekom.bmp.functional;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.google.inject.Inject;

import de.telekom.bmp.pages.GoogleLoginPage;
import de.telekom.bmp.pages.GoogleReadMailPage;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.controls.Link;

/**
 *
 * @author Mathias Herkt
 */
public class GoogleMailAccount {
    public static final String GOOGLE_MAIL_URL = "mail.google.com";
    public static final String MAIL_PASSWORD = "galerien3?";
    
    private String mail = "";
    private String password = "";
    
    @Inject
    Browser browser;
    
    @Inject
    GoogleLoginPage googlePage;

    @Inject
    GoogleReadMailPage readMailPage;
    
    public void setUsername(String login) {
        this.mail = login;
    }
    
    public void setPassword(String pw) {
        this.password = pw;
    }
    
    public String checkGoogleMailAccountAndExtractConfirmLink(String linkDomain) {
        browser.navigate().to(GOOGLE_MAIL_URL);
        
        loginIntoGoogleMailAccount();

        String confirmLink = readMailAndFindConfirmLink(linkDomain);
        
        logoutMailAccount();
        
        return confirmLink;
    }
    
    private void loginIntoGoogleMailAccount() {
        set(googlePage.email, extractEmailFromAlias(this.mail));
        set(googlePage.password, this.password);
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
            String mailName = emailAddress.substring(0, emailAddress.indexOf("+"));
            String domainName = emailAddress.substring(emailAddress.indexOf("@"));
            newMailAddress = mailName + domainName;
        }
        
        return newMailAddress;
    }
    
    private String readMailAndFindConfirmLink(String linkDomain) {
        Link confirmRegLnk = readMailPage.emailLinks.get(0);
        assertThat(confirmRegLnk, notNullValue());
        click(confirmRegLnk);
        
        String reallyConfirm = readMailPage.confirmLink.get(linkDomain).getHref();
        assertThat(reallyConfirm, notNullValue());
        
        return reallyConfirm;
    }
}
