package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.superuser.Dashboard;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.annotations.QCState.*;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Pierre Nomo, Daniel Biehl
 */
@QCId(value = "5496", state = Ready)
@UseWebDriver
public class TC004_Login_as_Superuser {

    @Inject
    BmpApplication app;

    @Inject
    FunctionalActions fa;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    Dashboard dashboard;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    // Needed user
    User user;

    @BeforeMethod
    public void preparation() {
        user = datapool.helpers().getSuperUser();

        navigateTo(app);
    }

    @Test(description = "this is the test")
    public void theTest() {
        assertThat("we have a user", user, is(not(nullValue())));

        click(header.login);

        set(login.username, user.email);
        set(login.password, user.password);
        click(login.signin);

        click(header.settings.superUser);

        verifyThat(dashboard, is(currentPage()));

        fa.logout();
    }
}
