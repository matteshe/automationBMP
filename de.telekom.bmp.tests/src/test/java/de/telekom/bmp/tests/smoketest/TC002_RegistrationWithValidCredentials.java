package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.GoogleLoginPage;
import de.telekom.bmp.pages.GoogleReadMailPage;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Signup;
import de.telekom.bmp.pages.accountsetup.AccountActivation;
import de.telekom.bmp.tests.GoogleMailAccount;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import java.util.Date;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@UseWebDriver
@QCId("123456")
public class TC002_RegistrationWithValidCredentials {
    
    public static final String APP_DOMAIN = "testcloud.bmptest.de";
    
    public static final String HTACCESS_CREDENTIALS = "toon:HullyGully";
    
    @Inject
    BmpApplication app;

    @Inject
    Home homePage;

    @Inject
    Signup signup;

    @Inject
    Datapool datapool;

    // Needed user
    User user;
    
    @Inject
    Browser browser;
        
    @Inject
    AccountActivation accountActivation;
    
    @Inject
    GoogleLoginPage loginPage;
    
    @Inject
    GoogleReadMailPage readMailPage;
    
    @BeforeTest
    public void setup() {
        // create a valid and not registered user
        user = createUser();        
        assertThat(user, notNullValue());
        
        navigateTo(app);
    }

    @Test
    public void test_RegistrationWithValidCredentials() throws InterruptedException {
       
        try {
            
// WORKAROUND BECAUSE OF CMS
            navigateTo(signup);
            
            click(homePage.registerBtn);            
            
            set(signup.emailAddress,user.email);
            
            assertThat("signup.iconValid.isDisplayed", signup.iconValid.isDisplayed());
            
            Thread.sleep(1000);
            
            // button to register
            click(signup.signup);
            
            
            
            user.registered = registerUser();
        } finally {            
            datapool.save(user);
        }
    }

    private boolean registerUser() throws InterruptedException {
        GoogleMailAccount mailAccount = new GoogleMailAccount(user.email, GoogleMailAccount.MAIL_PASSWORD)
                .browser(browser)
                .loginPage(loginPage)
                .readMailPage(readMailPage);
        
        String confirmLink = mailAccount.checkGoogleMailAccountAndExtractConfirmLink();
        assertThat(confirmLink, !confirmLink.equals(""));
        confirmLink = addHtaccessCredentials(confirmLink);
        
        browser.navigate().to(confirmLink);
        fillActivationForm();
        Thread.sleep(500);
        return true;
    }
    
    private void fillActivationForm() {
        set(accountActivation.firstName, "max");
        set(accountActivation.lastName, "mustermann" + 12345623);
        set(accountActivation.companyName, "nicht Telekom");
        set(accountActivation.password, "12345!QAY");
        set(accountActivation.confirmPassword, "12345!QAY");        
        click(accountActivation.termsAndCondition);        
        click(accountActivation.createAccountBtn);
    }

    private User createUser() {
        User newUser = new User();
        newUser.email = createMailAlias();
        newUser.registered = false;
        newUser.valid = true;
        
        return newUser;
    }

    private String createMailAlias() {
        long alias = (new Date()).getTime();
        return "mybmptestuser+" + alias + "@gmail.com";
    }

    private String addHtaccessCredentials(String link) {
        return link.replaceFirst("//", "//" + HTACCESS_CREDENTIALS + "@");
    }
}
