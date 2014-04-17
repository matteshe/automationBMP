package de.telekom.bmp.tests.tcs_04_loginAndRegistration;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.Signup;
import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static de.telekom.testframework.selenium.Matchers.displayed;
import static de.telekom.testframework.selenium.Matchers.displayed;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Nomop
 */
@UseWebDriver
@Test(groups = {"qcid-5506"})
public class TC03_Login_with_proper_not_registered_email {

    @Inject
    BmpApplication app;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    Signup signup;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    // Needed user
    User user;

    @BeforeMethod
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(true).get();

//        user = datapool.users().field("valid").equal(true).get();
        assertNotNull(user, "user not available");

//        user.valid = false;
        navigateTo(login);

    }

    @Test
    public void test_03_Login_with_proper_not_registered_email() {

        try {

            set(login.username, user.email);

            set(login.password, user.password);

            //login.signin.click();
            click(login.signin);

            assertThat(login.errorMsg, is(displayed()));

        } finally {
            datapool.save(user);
        }

    }

}
