package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.superuser.DashboardPage;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@QCId("5496")
@UseWebDriver
public class TC004_Login_as_Superuser {

    @Inject
    BmpApplication app;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    DashboardPage dashboardpage;

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
        assertNotNull(user, "cannot find a valid user");

        navigateTo(login);
    }

    @Test
    public void test_004_Login_as_Superuser() throws InterruptedException {

        try {
            set(login.usernameInput, user.email);

            set(login.passwordInput, user.password);

            click(login.signinBtn);

            click(header.settingsMenu.superuserLnk);

            // WORKAROUND
//            navigateTo(dashboardpage);
            assertThat(dashboardpage, isCurrentPage());

            click(dashboardpage.exceptionsTab);

            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
