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
import de.telekom.testframework.reporting.Reporter;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Pierre Nomo
 */

@QCId("5492")
@UseWebDriver
public class TC009_Create_Company_as_SSR {

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

    Date mailTimeStamp;
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
//        fa.ensureGermLanguageIsSet();

        mailTimeStamp = new Date();

    }

    @Test
    public void test_009_Create_Company_as_SSR() {

        try {

            fa.login(user);

//  NORMAL BEHAVIOUR
             
            click(header.settings.channelUser);
// WORKAROUND BECAUSE OF CMS
//            navigateTo(marketPlacePage);

            assertThat(marketPlacePage, is(currentPage()));

            click(marketPlacePage.browseByCompBtn);
            click(marketPlacePage.createNewCompBtn);

            set(marketPlacePage.companyName, "Company 130");
            set(marketPlacePage.phone, "06151111111");
            set(marketPlacePage.tdgCustomerNr, "12345678");

            // Company's Size Radio Button
            click(marketPlacePage.companySizeRadio);

            set(marketPlacePage.testerFirstName, "Tester");
            set(marketPlacePage.testerLastName, "SSR");

            // THE EMAIL should not be registered before.
            set(marketPlacePage.emailAdress, "dtagtester+ssr" + mailTimeStamp.getTime() + "@gmail.com");

            Reporter.reportMessage("dtagtester+ssr" + mailTimeStamp + "@gmail.com");

            // Click Checkboxes
            click(marketPlacePage.dataPermMarketingChBox);

            click(marketPlacePage.dataPermMarketingChBox);

            click(marketPlacePage.createFormCompBtn);

            assertThat(marketPlacePage.companyCreatedMsg, is(displayed()));

            // before Nested Classed introduced
            //click(header.account);
            //click(header.logout);
            click(header.account.logout);

            assertThat(home, is(currentPage()));

            //TODO Steps to confirm Company creation in Gmail 
        } finally {
            datapool.save(user);
        }

    }

}
