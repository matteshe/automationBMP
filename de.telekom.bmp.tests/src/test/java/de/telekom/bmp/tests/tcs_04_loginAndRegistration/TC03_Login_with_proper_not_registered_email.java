package de.telekom.bmp.tests.tcs_04_loginAndRegistration;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Login;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Nomop, Daniel Biehl
 */
@UseWebDriver
@QCId(value = "5539", state = QCState.Ready)
public class TC03_Login_with_proper_not_registered_email {

    @Inject
    BmpApplication app;

    @Inject
    FunctionalActions fa;

    @Inject
    Login login;

    @Inject
    Datapool datapool;

    // Needed user
    User user;

    @BeforeMethod
    public void setup() {
        user = datapool.helpers().defineNewTestUser("notRegistered");

        navigateTo(app);
        fa.ensureGermanLanguageIsSet();
    }

    @Test
    public void theTest() {
        // TODO update the ALM Testcase description
        assertThat("we have a valid user", user, is(not(nullValue())));

        navigateTo(login);

        set(login.username, user.email);
        set(login.password, user.password);
        click(login.signin);
        assertThat(login.feedbackPanel.error, is(displayed()));
        verifyThat(login.feedbackPanel.error, text(is("Benutzername/Passwort unbekannt")));
    }

}
