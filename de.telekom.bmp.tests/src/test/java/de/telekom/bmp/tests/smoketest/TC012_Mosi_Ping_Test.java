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
import de.telekom.bmp.pages.Mosi;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Pierre Nomo, Daniel Biehl
 *
 */
@UseWebDriver
@QCId(value = "5495", state = QCState.ReadyButNeedsReview)
public class TC012_Mosi_Ping_Test {

    @Inject
    BmpApplication app;

    @Inject
    FunctionalActions fa;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    Mosi mosiPage;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    // Needed user
    User user;

    @BeforeMethod
    public void preparation() {
        user = datapool.helpers().getSuperUser();

        assertThat("we have a valid user", user, is(not(nullValue())));

        fa.login(user);
    }

    @AfterMethod
    public void finalization() {
        fa.logout();
    }

    @Test
    public void theTest() {
        navigateTo(mosiPage);
        assertThat(mosiPage, is(currentPage()));

        click(mosiPage.pingMOSI);

        assertThat(mosiPage.feedbackPanel.info, text(is("Successfully called MOSI ping API. Received response: \"true\".")));
    }

}
