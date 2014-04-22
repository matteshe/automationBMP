package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.channel.MarketPlacePage;
import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.Assert.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.annotations.QCState.*;
import de.telekom.testframework.reporting.Reporter;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.is;


import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Pierre Nomo
 */

@QCId(value = "5491", state = Ongoing)
@UseWebDriver
public class TC008_SSR_Start_Purchase_for_Customer {

    @Inject
    BmpApplication app;
    
    @Inject
    FunctionalActions fa;

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

    @BeforeMethod
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
            fa.login(user);

//  NORMALES VERHALTEN
             
            click(header.settings.channelUser);

// WORKAROUND
//            navigateTo(marketPlacePage);
            assertThat(marketPlacePage, is(currentPage()));

            // TODO Automation Test Steps
            Reporter.reportMessage("Automations Test Steps are missing....");
            
            fa.logout();

            assertThat(home, is(currentPage()));
            
            fail("Automations Test Steps are missing....");

        } finally {
            datapool.save(user);
        }

    }

}
