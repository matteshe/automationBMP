package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Signup;
import de.telekom.bmp.pages.accountsetup.AccountActivationPage;
import de.telekom.bmp.tests.GoogleMailAccount;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import java.util.Date;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.AfterTest;
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
    AccountActivationPage accountActivation;

    @Inject
    GoogleMailAccount mailAccount;

    @BeforeTest
    public void setup() {
        // create a valid and not registered user
        user = createUser();
        assertThat(user, notNullValue());

        navigateTo(app);
    }

    @AfterTest
    public void tearDown() {
        datapool.save(user);
    }

    @Test
    public void test_RegistrationWithValidCredentials() throws InterruptedException {
        navigateTo(signup);

        click(homePage.registerBtn);

        set(signup.emailAddress, user.email);

        assertThat("signup.iconValid.isDisplayed", signup.iconValid.isDisplayed());

        Thread.sleep(1000);

        // button to register
        click(signup.signup);

        user.registered = registerUser();
    }

    private boolean registerUser() throws InterruptedException {
        mailAccount.setUsername(user.email);
        mailAccount.setPassword(GoogleMailAccount.MAIL_PASSWORD);

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
        set(accountActivation.lastName, user.name);
        set(accountActivation.companyName, "nicht Telekom");
        set(accountActivation.password, user.password);
        set(accountActivation.confirmPassword, "12345!QAY");
        click(accountActivation.termsAndCondition);
        click(accountActivation.createAccountBtn);
    }

    private User createUser() {
        User newUser = new User();
        newUser.email = createMailAlias();
        newUser.password = "12345!QAY";
        newUser.name = "mustermann";
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
