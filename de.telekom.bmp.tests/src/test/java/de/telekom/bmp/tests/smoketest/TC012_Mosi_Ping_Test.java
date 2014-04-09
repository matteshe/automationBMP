package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MosiPage;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@UseWebDriver
//@Test(groups = {"qcid-5506"})
@QCId("5495")
public class TC012_Mosi_Ping_Test {

    @Inject
    BmpApplication app;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    MosiPage mosiPage;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    // Needed user
    User user;

    @BeforeTest
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("bmptestuser@gmail.com").get();

        assertThat("we have a valid user", user, is(not(nullValue())));

//        user.valid = false;
        navigateTo(login);

        // sets the englisch language
//        if (header.languageToogleEN.isDisplayed()) {
//            click(header.languageToogleEN);
//        }
    }

    @Test
    public void test_012_Mosi_Ping_Test() {

        try {
            set(login.usernameInput, user.email);

            set(login.passwordInput, user.password);

            click(login.signinBtn);

            navigateTo(mosiPage);
            assertThat(mosiPage, isCurrentPage());

            click(mosiPage.pingMOSIBtn);

            if (!mosiPage.pingMOSISuccessfullTxt.isDisplayed()) {
                fail("Text: " + mosiPage.pingMOSISuccessfullTxt.getText() + "not displayed!");
            }

            // before Nested Classed introduced
            //click(header.account);
            //click(header.logout);
            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
