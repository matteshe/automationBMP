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
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.controls.Link;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@UseWebDriver
@QCId("123456")
public class TC002_RegistrationWithValidCredentials {

    public static final String MAIL_PASSWORD = "galerien3?";
    
    public static final String APP_DOMAIN = "testcloud.bmptest.de";
    
    public static final String HTACCESS_CREDENTIALS = "toon:HullyGully";
            
    public static final String GOOGLE_MAIL_URL = "mail.google.com";
    
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
    GoogleLoginPage googlePage;
    
    @Inject
    Browser browser;
    
    @Inject
    GoogleReadMailPage readMailPage;
    
    @Inject
    AccountActivation accountActivation;

    @BeforeTest
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").equal(false).get();
        
        assertNotNull(user, "cannot find a valid user");
        
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
            
            checkGoogleMailAccount();
            
//            user.registered = true;
//            user.valid = true;
        } finally {            
            datapool.save(user);
        }
    }

    private void checkGoogleMailAccount() throws InterruptedException {
        browser.navigate().to(GOOGLE_MAIL_URL);
        
        loginIntoGoogleMailAccount();

        String confirmLink = readMailAndFindConfirmLink();
        
        confirmLink = confirmLink.replaceFirst("//", "//" + HTACCESS_CREDENTIALS + "@");
        
        browser.navigate().to(confirmLink);
        fillActivationForm();
        
        Thread.sleep(8000);
    }
    
    private String extractEmailFromAlias(String emailAdress) {
        String mailName = emailAdress.substring(0, emailAdress.indexOf("+"));
        String domainName = emailAdress.substring(emailAdress.indexOf("@"));
        return mailName + domainName;
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

    private void loginIntoGoogleMailAccount() {
        set(googlePage.email, extractEmailFromAlias(user.email));
        set(googlePage.password, MAIL_PASSWORD);
        click(googlePage.loginBtn);
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
