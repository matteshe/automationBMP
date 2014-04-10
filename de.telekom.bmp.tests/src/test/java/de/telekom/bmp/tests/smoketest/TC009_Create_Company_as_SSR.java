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

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@QCId("5492")
@UseWebDriver
public class TC009_Create_Company_as_SSR {

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

//        // sets the englisch language
//        if (header.languageToogleEN.isDisplayed()) {
//            click(header.languageToogleEN);
//        }
        mailTimeStamp = new Date();

    }

    @Test
    public void test_009_Create_Company_as_SSR() {

        try {

            set(login.usernameInput, user.email);

            set(login.passwordInput, user.password);

            click(login.signinBtn);

// WORKAROUND NORMAL BEHAVIOUR
//             
//            click(header.settings);
//            click(header.channelUser);
// WORKAROUND
            navigateTo(marketPlacePage);

            assertThat(marketPlacePage, isCurrentPage());

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

            System.out.println("dtagtester+ssr" + mailTimeStamp + "@gmail.com");

            // Click Checkboxes
            click(marketPlacePage.dataPermMarketingChBox);

            click(marketPlacePage.dataPermMarketingChBox);

            click(marketPlacePage.createFormCompBtn);

            assertThat(marketPlacePage.companyCreatedMsg, is(displayed()));

            // before Nested Classed introduced
            //click(header.account);
            //click(header.logout);
            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());

            //TODO Steps to confirm Company creation in Gmail 
        } finally {
            datapool.save(user);
        }

    }

}
