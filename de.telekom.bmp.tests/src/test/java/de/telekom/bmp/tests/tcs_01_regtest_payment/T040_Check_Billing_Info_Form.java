/**
 *
 */
package de.telekom.bmp.tests.tcs_01_regtest_payment;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.set;

import javax.inject.Inject;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.AccountHandling;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.account.Account;
import de.telekom.bmp.pages.account.Bills;
import de.telekom.testframework.Assert;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

/**
 * @author Mathias Herkt
 *
 */
@UseWebDriver
@QCId("4013")
public class T040_Check_Billing_Info_Form {

    private static final String MAIL_PREFIX = "mybmptestuser";
    @Inject
    Datapool db;

    @Inject
    AccountHandling accHandling;

    @Inject
    Account accountPage;

    @Inject
    Bills billPage;

    @Inject
    FunctionalActions fa;

    @Inject
    Header headerPage;

    private User user;

    @BeforeMethod
    public void setup() throws InterruptedException {
        user = db.users().field("registered").equal(true).field("valid")
                .equal(false).get();
        if (user == null) {
            user = User.createUser(MAIL_PREFIX);
            accHandling.registerAccount(user);
            db.save(user);
        }
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void setupPaymentData() {
        fa.login(user.email, user.password);

        // einstellungen->unternehmen
        click(headerPage.settings.account);

        click(accountPage.tabs.billing);
        click(billPage.paymentDetails);

        setupPaymentDetails();

        // verify setup was successful
        Assert.assertThat("Setup payment details was successful",
                billPage.feedbackPanelInfo.isDisplayed());

        fa.logout();
    }

    private void setupPaymentDetails() {
        set(billPage.addressInput, "STREET");
        set(billPage.cityInput, "CITY");
        set(billPage.postcodeInput, "11111");
        set(billPage.country, "Deutschland");
        // state will be dynamically loaded based on country. wait shortly
        waitForMillSec(500);
        set(billPage.state, "Hessen");
        set(billPage.vatInput, "DE999999999");
        click(billPage.submit);
    }

    private void waitForMillSec(long value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
