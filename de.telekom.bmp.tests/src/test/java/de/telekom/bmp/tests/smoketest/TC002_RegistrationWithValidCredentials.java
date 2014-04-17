package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.EMailAccount;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Signup;
import de.telekom.bmp.pages.accountsetup.AccountActivationPage;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.annotations.QCState.NeedsReview;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UseWebDriver
@QCId(value = "5506", state = NeedsReview)
public class TC002_RegistrationWithValidCredentials {

    @Inject
    BmpApplication app;

    @Inject
    Header header;

    @Inject
    Signup signup;

    @Inject
    Home home;

    @Inject
    AccountActivationPage accountActivation;

    @Inject
    Datapool datapool;

    User user;

    @BeforeMethod
    public void setup() {
        navigateTo(app);

        user = datapool.validUsers().field(User.Fields.registered).equal(false).get();
        if (user == null) {
            user = datapool.helpers().createTestUser();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (user != null) {
            datapool.save(user);
        }
    }

    @Test
    public void registrationWithValidCredentials() throws InterruptedException {
        assertThat("we have a user", user, is(not(nullValue())));
        user.valid = false;

        navigateTo(signup);

        click(header.register);

        set(signup.signupForm.emailAddress, user.email);
        assertThat(signup.signupForm.iconValid, is(displayed()));

        click(signup.signupForm.signup);

        verifyThat(signup.signupConfirmationPanel.thanks, is(displayed()));

        String confirmLink = EMailAccount.getConfirmationLink(user);

        assertThat("we have a confirmation link", confirmLink, not(isEmptyOrNullString()));
        assertThat("the confirmation link is a valid url", confirmLink, is(url()));

        navigateTo(confirmLink);

        set(accountActivation.firstName, user.firstName);
        set(accountActivation.lastName, user.name);

        set(accountActivation.companyName, user.company.name);
        set(accountActivation.industry, user.company.industry);

        set(accountActivation.phoneNumber, user.phoneNumber);

        set(accountActivation.password, user.password);
        set(accountActivation.confirmPassword, user.password);

        set(accountActivation.termsAndCondition, true);

        click(accountActivation.createAccountBtn);

        assertThat(home, is(currentPage()));

        // TODO further verifications needed in manual test case description, see HP ALM
        click(header.accountMenu.logout);

        user.registered = true;
        user.valid = true;
    }
}
