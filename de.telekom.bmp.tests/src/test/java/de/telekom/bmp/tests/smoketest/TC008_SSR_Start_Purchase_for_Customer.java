package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.channel.MarketPlacePage;
import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.Assert.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

import org.testng.Assert;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@UseWebDriver
@QCId("5491")
public class TC008_SSR_Start_Purchase_for_Customer {

    @Inject
    BmpApplication app;

    @Inject
    Login login;
    @Inject
    Home home;

    @Inject
    Header header;

    @Inject
    MarketPlacePage marketPlacePage;

    @Inject
    Datapool datapool;

    // Needed user
    User user;

    @BeforeTest
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("mybmptestuser+ssr@gmail.com").get();

        assertNotNull(user, "cannot find a valid user");

//        user.valid = false;
        navigateTo(login);

        // sets the englisch language
//        if (header.languageToogleEN.isDisplayed()) {
//            click(header.languageToogleEN);
//        }

    }

    @Test
    public void test_008_SSR_Start_Purchase_for_Customer() {

        try {
            set(login.usernameInput, user.email);

            set(login.passwordInput, user.password);

            click(login.signinBtn);

// WORKAROUND NORMALES VERHALTEN
//             
//            click(header.settings);
//            click(header.channelUser);

// WORKAROUND
            navigateTo(marketPlacePage);
            assertThat(marketPlacePage, isCurrentPage());

            // TODO Automation Test Steps
            Assert.fail("Automations Test Steps are missing....");
            
            // before Nested Classed introduced
            //click(header.account);
            //click(header.logout);
            
            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());

        } finally {
            datapool.save(user);
        }

    }

}
