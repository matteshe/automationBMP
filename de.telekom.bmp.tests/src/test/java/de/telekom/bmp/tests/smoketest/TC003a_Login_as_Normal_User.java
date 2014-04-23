package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
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
 * @author Pierre Nomo, Daniel Biehl
 */
@QCId(value = "4123", state = QCState.Ready)
@UseWebDriver
public class TC003a_Login_as_Normal_User {

    @Inject
    BmpApplication app;

    @Inject
    FunctionalActions fa;

    @Inject
    Login login;

    @Inject
    Header header;

    @Inject
    Home home;

    @Inject
    Datapool datapool;

    // Needed user
    User user;

    @BeforeMethod
    public void preparation() {
        navigateTo(app);

        user = datapool.validUsers()
                .field(User.Fields.registered).equal(true)
                .field(User.Fields.role).equal(UserRole.USER)
                .field(User.Fields.applications).doesNotExist().get();
    }

    @Test
    public void theTest() throws InterruptedException {
        assertThat("we have a user", user, is(not(nullValue())));

        click(header.login);
        set(login.username, user.email);
        set(login.password, user.password);
        click(login.signin);

        verifyThat(home, is(currentPage()));

        fa.logout();
    }

}
