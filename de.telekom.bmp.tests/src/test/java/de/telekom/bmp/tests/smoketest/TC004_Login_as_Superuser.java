package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
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

@QCId(value = "5496", state = Ready)
@UseWebDriver
public class TC004_Login_as_Superuser {

    @Inject
    BmpApplication app;

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
    public void setup() {
        navigateTo(app);
        user = datapool.validUsers()
                .field(User.Fields.registered).notEqual(false)
                .field(User.Fields.role).equal(UserRole.SUPERUSER).get();

        if (user == null) {
            user = datapool.helpers().getSuperuser();
        }
    }

    @Test
    public void test_004_Login_as_Superuser() throws InterruptedException {
        assertThat("we have a user", user, is(not(nullValue())));

        click(header.login);

        set(login.username, user.email);
        set(login.password, user.password);
        click(login.signin);

        click(header.settings.superUser);

        verifyThat(dashboard, is(currentPage()));

        click(header.account.logout);
    }
}
