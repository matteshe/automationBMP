package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.ExchangeOnlinePage;
import de.telekom.bmp.pages.FindApplicationsPage;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.ProductSettingsForOffice365Page;
import de.telekom.bmp.pages.PurchasePage;
import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.Assert.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Pierre Nomo
 */

@UseWebDriver
@QCId("5493")
public class TC013_Booking_Exchange_Online_Plan_1 {

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
    FindApplicationsPage findAppsPage;

    @Inject
    ExchangeOnlinePage exchangeOnlinePage;

    @Inject
    PurchasePage purchasePage;

    @Inject
    ProductSettingsForOffice365Page productSettingsForOffice365Page;

    @Inject
    Datapool datapool;

    // Needed user
    User user;

    @BeforeMethod
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("mybmptestuser+normaluser@gmail.com").get();

        assertNotNull(user, "cannot find a valid user");

//        user.valid = false;
        navigateTo(login);

    }

    @Test
    public void test_013_Booking_Exchange_Online_Plan_1() {

        try {
            fa.login(user);

            //TODO configure search Text. Properties File?
            set(header.searchInput, "exchange");
            click(header.searchBtn);

            // WORKAROUND BECAUSE OF CMS
            navigateTo(findAppsPage);

            assertThat(findAppsPage, is(currentPage()));

            click(findAppsPage.exchangeProduktLnk);

            assertThat(exchangeOnlinePage, is(currentPage()));

            click(exchangeOnlinePage.buyNowBtn);

            assertThat(purchasePage, is(currentPage()));

            set(purchasePage.numberOfUserTxt, "1");

            click(purchasePage.continueBtn);

            //           assertThat(productSettingsForOffice365Page, page());
            set(productSettingsForOffice365Page.mosiDomainInput, "130hfmaxp");

            click(productSettingsForOffice365Page.continueBtn);

            set(productSettingsForOffice365Page.line1Input, "test");
            set(productSettingsForOffice365Page.line2Input, "test");
            set(productSettingsForOffice365Page.postalCodeInput, "12345");

            set(productSettingsForOffice365Page.cityInput, "Stuttgart");
            set(productSettingsForOffice365Page.provinceCbox, "Baden-Württemberg");

            set(productSettingsForOffice365Page.phoneNumberInput, "+49 6151 6804448");

            click(productSettingsForOffice365Page.continueStep2Btn);

//            click(productSettingsForOffice365Page.account);
//            click(productSettingsForOffice365Page.logout);
            fa.logout();

            assertThat(home, is(currentPage()));

        } finally {
            datapool.save(user);
        }

    }

}
