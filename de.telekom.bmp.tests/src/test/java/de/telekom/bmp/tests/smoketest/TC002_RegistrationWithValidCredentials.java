package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.AccountHandling;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Signup;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UseWebDriver
@QCId("5506")
public class TC002_RegistrationWithValidCredentials {

    @Inject
    AccountHandling accountHandling;

    @Inject
    BmpApplication app;

    @Inject
    Header header;

    @Inject
    Signup signup;

    @Inject
    Datapool datapool;

    User user;

    @BeforeMethod
    public void setup() {
        navigateTo(app);

        user = datapool.validUsers().field(User.Fields.registered).equal(false).get();
        if (user == null) {
            user = User.createUser();
        }
    }

    @AfterMethod
    public void tearDown() {
        datapool.save(user);
    }

    @Test
    public void registrationWithValidCredentials() throws InterruptedException {
        assertThat("we have a user", user, is(not(nullValue())));
        user.valid = false;

        //accountHandling.registerAccount(user);
        
        
//        navigateTo(signup);
//
//        click(header.register);
//
//        set(signup.signupForm.emailAddress, user.email);
//        assertThat(signup.signupForm.iconValid, is(displayed()));
//        click(signup.signupForm.signup);
//
//        verifyThat(signup.signupConfirmationPanel.thanks, is(displayed()));
//
        String confirmLink;

        confirmLink = accountHandling.getConfirmLink(user.email);

        assertThat(confirmLink, not(isEmptyOrNullString()));

        navigateTo(confirmLink);

        //user.registered = true;
        user.valid = true;
    }
}
