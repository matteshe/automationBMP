package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.annotations.QCState.ReadyButNeedsReview;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@QCId(value="4123", state = ReadyButNeedsReview)
@UseWebDriver
public class TC003a_Login_as_Normal_User {

    @Inject
    BmpApplication app;

    @Inject
    Login login;
   
    @Inject
    Datapool datapool;

    @Inject
    Header header;

    @Inject
    Home home;

    // Needed user
    User user;

    @BeforeMethod
    public void setup() {
        navigateTo(app);

        user = datapool.validUsers()
                .field(User.Fields.registered).equal(true)
                .field(User.Fields.role).equal(UserRole.USER)
                .field(User.Fields.applications).doesNotExist().get();
    }

    @Test
    public void test_003a_Login_as_Normal_User() throws InterruptedException {
        assertThat("we have a user", user, is(not(nullValue())));

        click(header.login);

        set(login.username, user.email);
        set(login.password, user.password);
        click(login.signin);

        verifyThat(home, isCurrentPage());

        // TODO further verifications needed in manual test case description, see HP ALM
        click(header.account.logout);
    }

}
