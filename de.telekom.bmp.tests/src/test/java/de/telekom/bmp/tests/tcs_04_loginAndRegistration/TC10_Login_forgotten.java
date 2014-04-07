
package de.telekom.bmp.tests.tcs_04_loginAndRegistration;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.GoogleMailAccount;
import de.telekom.bmp.pages.ForgotPasswordPage;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.ResetPasswordPage;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author Mathias Herkt
 */

@UseWebDriver
@Test(groups = {"qcid-3566"})
public class TC10_Login_forgotten {
    private static final String HTACCESS_CREDENTIALS = "toon:HullyGully";

    private static final String APP_DOMAIN = "testcloud.bmptest.de";
    
    @Inject
    Browser browser;
    
    @Inject
    BmpApplication app;

    @Inject
    Datapool db;
    
    @Inject
    Login login;
    
    @Inject
    GoogleMailAccount mailAccount;
    
    @Inject
    ForgotPasswordPage forgotPwPage;
    
    @Inject
    ResetPasswordPage resetPwPage;
    
    private User user;

    @BeforeTest
    public void setup() {
        // get registered users
        user = db.users().field("registered").equal(true).field("email").equal("mybmptestuser+1396600497570@gmail.com").get();
        assertThat(user, notNullValue());

        navigateTo(login);
    }
    
    @AfterTest
    public void tearDown() {
        db.save(user);
    }

    @Test
    public void askForPasswordReset() {
        click(login.forgotPasswordLnk);
        
        set(forgotPwPage.email, user.email);
        click(forgotPwPage.sendMailBtn);
        
        mailAccount.setUsername(user.email);
        mailAccount.setPassword(GoogleMailAccount.MAIL_PASSWORD);
        
        String setNewPasswordLink = mailAccount.checkGoogleMailAccountAndExtractConfirmLink(APP_DOMAIN);
        
        assertThat(setNewPasswordLink, !setNewPasswordLink.equals(""));
        setNewPasswordLink = addHtaccessCredentials(setNewPasswordLink);
        
        browser.navigate().to(setNewPasswordLink);
        
        resetPassword();
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            // do nothing
        }
    }

    private String addHtaccessCredentials(String link) {
        return link.replaceFirst("//", "//" + HTACCESS_CREDENTIALS + "@");
    }

    private void resetPassword() {
        if (user.password == null || "".equals(user.password))
        {
            user.password = "1234!QAY";
        } else {
            user.password = new StringBuilder(user.password).reverse().toString();
        }
        
        set(resetPwPage.password,user.password);
        set(resetPwPage.confirmPassword, user.password);
        click(resetPwPage.submitBtn);
    }
}
