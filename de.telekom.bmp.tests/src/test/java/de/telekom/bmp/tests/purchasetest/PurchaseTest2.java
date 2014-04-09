package de.telekom.bmp.tests.purchasetest;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.*;
import de.telekom.bmp.data.App;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.pages.purchase.BillingDetails;
import de.telekom.bmp.pages.purchase.CreateOrder;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.functional.FunctionalActions2;

import de.telekom.bmp.pages.purchase.ConfirmOrder;
import de.telekom.bmp.pages.purchase.CreateOrder.EditionRow;
import de.telekom.bmp.pages.purchase.OrderReceipt;
import de.telekom.bmp.pages.purchase.PaymentSelection;
import de.telekom.bmp.pages.purchase.TelekomPayment;

import static de.telekom.testframework.Actions.*;

import static de.telekom.testframework.reporting.Reporter.reportMessage;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */
@UseWebDriver
public class PurchaseTest2 {

    @Inject
    FunctionalActions functional;
    
    @Inject
    FunctionalActions2 functional2;

    @Inject
    CreateOrder createOrder;

    @Inject
    BillingDetails billingDetails;

    @Inject
    ConfirmOrder confirmOrder;

    @Inject
    PaymentSelection paymentSelect;

    @Inject
    TelekomPayment telekomPay;

    @Inject
    OrderReceipt orderReceipt;

    @Inject
    Datapool datapool;

    @BeforeMethod
    public void setup() {

    }
    
    @Test
    public void test(){
    
        functional.login("dtagtester01+1374hf1@googlemail.com", "baum1301");
        functional2.purchase("1260", "Medium");
        
    }

    @Test
    public void test1() throws InterruptedException {

        App app = datapool.apps().field("testID").equal("3787").get();

        assertThat("we have a valid App", app != null);

        User user = datapool.users()
                .field("valid").equal(true)
                .field("registered").equal(true)
                .field("role").equal(UserRole.USER)
                //                .field("name").equal("NormalUserWithApps")
                .field("apps").notEqual(app.appID).get();

        assertThat("we have a valid user", user != null);

        functional.login(user.email, user.password);

        System.out.println("----" + app.appID + "----" + app.name + "----" + app.testID + "----");

        navigateTo(createOrder, "/" + app.appID);

        verifyThat(createOrder.createOrderForm.isDisplayed());

//       for (Link link: portal.categories ) {
//           reportMessage(link.getAttribute("title"));
//       }
        for (EditionRow edition : createOrder.editions) {
            //reportMessage(button.   ..getAttribute("title"));
            //reportMessage(edition.name.getText());
            if (edition.name.getText().contains("Small")) {
                reportMessage(edition.name.getText());
                //edition.name.click();
                click(edition.name);
            }

        }


        click(createOrder.nextStep);

        verifyThat(billingDetails, isCurrentPage());
        set(billingDetails.salutation, "COMPANY");

        set(billingDetails.company, "Autom Company");
        set(billingDetails.lastName, "Autom User");
        set(billingDetails.address, "Telekom");
        set(billingDetails.city, "Darmstadt");
        set(billingDetails.postCode, "64295");
        set(billingDetails.country, "DE");
        set(billingDetails.state, "DE-HE");
        click(billingDetails.nextStep);
       //click(billingDetails.goBack);

        //HIER COPAS FLOW HINZUFÜGEN
        click(paymentSelect.paymentRadioSelect("Kreditkarte"));
        click(paymentSelect.paymentRadioSelect("Telekom Festnetzrechnung"));
        click(paymentSelect.nextBtn);

        set(telekomPay.usernameTextF, "bmp_qa1");
        set(telekomPay.passwordTextF, "svep");

        click(telekomPay.loginBtn);

        //Place Order
        reportMessage(confirmOrder.BruttoValue.getText());
        reportMessage(confirmOrder.RecurringValue.getText());
        Thread.sleep(2000);
        click(confirmOrder.placeOrderBtn);

        click(orderReceipt.goToMyApps);

        user.apps.add(app.appID);
        datapool.save(user);
    }

    @AfterMethod
    public void teardown() {
        functional.logout();
    }
}
