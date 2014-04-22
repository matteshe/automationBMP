package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.DeveloperCreatePage;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MyApps;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Pierre Nomo
 */

@QCId("5533")
@UseWebDriver
public class TC006b_Login_as_Developer_With_Apps {

    @Inject
    BmpApplication app;
    
    @Inject
    FunctionalActions fa;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    MyApps myapps;

    @Inject
    DeveloperCreatePage devCreatePg;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    // Needed user
    User user;

    @BeforeMethod
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("mybmptestuser+devwithapp@gmail.com").get();

        assertNotNull(user, "cannot find a valid user");

//        user.valid = false;
        navigateTo(login);

    }

    @Test
    public void test_006b_Login_as_Developer_With_Apps() {

        try {
            fa.login(user);

            assertThat(myapps, is(currentPage()));

// WORKAROUND BECAUSE OF CMS
//            navigateTo(devCreatePg);
            click(header.settings.developer);

            assertThat(devCreatePg, is(currentPage()));

            click(devCreatePg.produkteLnk);
            click(devCreatePg.rechnungenLnk);
            click(devCreatePg.integrationergnLnk);
            click(devCreatePg.documentationLnk);
            click(devCreatePg.auszahlungenLnk);

            fa.logout();

            assertThat(home, is(currentPage()));

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
