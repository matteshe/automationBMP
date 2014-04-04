
package de.telekom.bmp.tests.tcs_04_loginAndRegistration;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.ForgotPasswordPage;
import de.telekom.bmp.pages.GoogleLoginPage;
import de.telekom.bmp.pages.GoogleReadMailPage;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.tests.GoogleMailAccount;
import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.Actions.navigateTo;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author Mathias Herkt
 */

@UseWebDriver
@Test(groups = {"qcid-3566"})
public class TC10_Login_forgotten {
    public static final String HTACCESS_CREDENTIALS = "toon:HullyGully";

    @Inject
    Browser browser;
    
    @Inject
    BmpApplication app;

    @Inject
    Datapool db;
    
    @Inject
    Login login;
    
    @Inject
    GoogleLoginPage loginPage;
    
    @Inject
    GoogleReadMailPage readMailPage;
    
    @Inject
    ForgotPasswordPage forgotPwPage;
    
    private User user;

    @BeforeTest
    public void setup() {
        // get registered users
        user = db.users().field("registered").equal(true).get();
        
        assertNotNull(user, "user not available");

        navigateTo(login);
    }

    @Test
    public void askForPasswordReset() {
        click(login.forgotPasswordLnk);
        
        set(forgotPwPage.email, user.email);
        click(forgotPwPage.sendMailBtn);
        
        GoogleMailAccount mailAccount = new GoogleMailAccount(user.email, GoogleMailAccount.MAIL_PASSWORD)
                .browser(browser)
                .loginPage(loginPage)
                .readMailPage(readMailPage);
        
        String setNewPasswordLink = mailAccount.checkGoogleMailAccountAndExtractConfirmLink();
        
        assertThat(setNewPasswordLink, !setNewPasswordLink.equals(""));
        setNewPasswordLink = addHtaccessCredentials(setNewPasswordLink);
        
        browser.navigate().to(setNewPasswordLink);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            // do nothing
        }
    }

    private String addHtaccessCredentials(String link) {
        return link.replaceFirst("//", "//" + HTACCESS_CREDENTIALS + "@");
    }
}
