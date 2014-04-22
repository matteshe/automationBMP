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
import de.telekom.bmp.pages.MosiPage;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UseWebDriver
@QCId("5495")
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
    MosiPage mosiPage;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    // Needed user
    User user;

    @BeforeMethod
    public void setup() {
        user = datapool.validUsers()
                .field(User.Fields.registered).notEqual(false)
                .field(User.Fields.role).equal(UserRole.SUPERUSER).get();

        if (user == null) {
            user = datapool.helpers().getSuperuser();
        }

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
           fa.login(user);

            navigateTo(mosiPage);
            assertThat(mosiPage, is(currentPage()));

            click(mosiPage.pingMOSIBtn);

            assertThat(mosiPage.pingMOSISuccessfullTxt, is(displayed()));
            
            fa.logout();

            assertThat(home, is(currentPage()));

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
