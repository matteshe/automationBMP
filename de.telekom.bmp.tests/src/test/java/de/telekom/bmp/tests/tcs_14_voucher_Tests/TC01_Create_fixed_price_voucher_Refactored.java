package de.telekom.bmp.tests.tcs_14_voucher_Tests;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.channel.DiscountsPage;
import de.telekom.bmp.pages.channel.MarketPlacePage;
import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UseWebDriver
@Test(groups = {"qcid-5506"})
public class TC01_Create_fixed_price_voucher_Refactored {

    @Inject
    BmpApplication app;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    MarketPlacePage marketplacepage;

    @Inject
    DiscountsPage discountsPage;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    @Inject

    // Needed user
    User user;

    @BeforeMethod
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("bmptestuser@gmail.com").get();

        assertNotNull(user, "cannot find a valid user");

//        user.valid = false;
        navigateTo(login);

        // sets the englisch language
//        if (header.languageToogleEN.isDisplayed()) {
//            click(header.languageToogleEN);
//        }
    }

    @Test
    public void test_01_Create_fixed_price_voucher_Refactored() throws InterruptedException {

        try {
            set(login.username, user.email);

            set(login.password, user.password);

            click(login.signin);

// WORKAROUND NORMALES VERHALTEN
//             
//            click(header.settings);
//            click(header.channelUser);
// WORKAROUND
            navigateTo(marketplacepage);

            assertThat(marketplacepage, isCurrentPage());

            click(marketplacepage.productsTab);

            click(marketplacepage.leftLinks.get("Rabatte"));

            click(discountsPage.rabattCodeHinzBtn);

            click(discountsPage.codeOpt);

            set(discountsPage.codeInput, "test_12345-üöa");

            //click(discountsPage.discountPriceOpts.get("radio325"));
            click(discountsPage.fixPriceOpt);

//click(marketplacepage.settingsTab);
            Thread.sleep(5000);

            // before Nested Classed introduced
            //click(header.account);
            //click(header.logout);
            click(header.account.logout);

            assertThat(home, isCurrentPage());

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
