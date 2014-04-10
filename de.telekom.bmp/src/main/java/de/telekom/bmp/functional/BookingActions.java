package de.telekom.bmp.functional;

import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MyApps;
import de.telekom.bmp.pages.purchase.BillingDetails;
import de.telekom.bmp.pages.purchase.ConfirmOrder;
import de.telekom.bmp.pages.purchase.CreateOrder;
import de.telekom.bmp.pages.purchase.OrderReceipt;
import de.telekom.bmp.pages.purchase.PaymentSelection;
import de.telekom.bmp.pages.purchase.TelekomPayment;
import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.reporting.Reporter.reportMessage;
import static de.telekom.testframework.selenium.Matchers.*;
import javax.inject.Inject;
import static org.hamcrest.Matchers.is;

/**
 *
 * @author Habib, Tatiana
 */
public class BookingActions {

    @Inject
    Login login;

    @Inject
    Header header;

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
    MyApps launchpad;

    public void createOrder(String appID, String editionName) {
        navigateTo(createOrder, "/" + appID);

        //verifyThat(createOrder, isCurrentPage());
//        for (CreateOrder.EditionRow edition : createOrder.editions) {
//            //reportMessage(button.   ..getAttribute("title"));
//            //reportMessage(edition.name.getText());
//            if (edition.name.getText().contains(editionName)) {
//            //if (edition.name, attribute("title", is(editionName))) {
//                reportMessage(edition.name.getText());
//                click(edition.name);
//            }
//        }
//        for (int i = 0; i < createOrder.editions.size(); i++) {
//            //reportMessage(button.   ..getAttribute("title"));
//            //reportMessage(edition.name.getText());
//            if (createOrder.editions.get(i).name.getText().contains(editionName)) {
//                //if (edition.name, attribute("title", is(editionName))) {
//                reportMessage(createOrder.editions.get(i).name.getText());
//                click(createOrder.editions.get(i).name);
//            }
//        }

        click(createOrder.edition.get(editionName).name);
        click(createOrder.nextStep);

    }

    public void enterBillingDetails() {

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

    }

    public void paymentTelekomBill() {
        click(paymentSelect.paymentRadioSelect("Telekom Festnetzrechnung"));
        click(paymentSelect.nextBtn);

        int i = (int) Math.random() * 5 + 1;
        set(telekomPay.usernameTextF, "bmp_qa" + i);
        set(telekomPay.passwordTextF, "svep");

        click(telekomPay.loginBtn);
    }

    public void confirmOrder() {

        //verifyThat(confirmOrder, isCurrentPage());
        reportMessage(confirmOrder.BruttoValue.getText());
        reportMessage(confirmOrder.RecurringValue.getText());
        click(confirmOrder.placeOrderBtn);

    }

    public void orderReceipt() {

        //verifyThat(orderReceipt, isCurrentPage());
        click(orderReceipt.goToMyApps);
        verifyThat(launchpad, isCurrentPage());

    }

    public void purchase(String appID, String editionName) {
        navigateTo(createOrder, "/" + appID);

        verifyThat(createOrder.createOrderForm.isDisplayed());

//       for (Link link: portal.categories ) {
//           reportMessage(link.getAttribute("title"));
//       }
        for (CreateOrder.EditionRow edition : createOrder.editions) {
            //reportMessage(button.   ..getAttribute("title"));
            //reportMessage(edition.name.getText());
            if (edition.name.getText().contains(editionName)) {
                reportMessage(edition.name.getText());
                //edition.name.click();
                click(edition.name);
            }
        }

        click(createOrder.nextStep);

        verifyThat(billingDetails, isCurrentPage());
        set(billingDetails.salutation, "COMPANY");
        verifyThat(billingDetails, isCurrentPage());
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

        int i = (int) Math.random() * 5 + 1;
        set(telekomPay.usernameTextF, "bmp_qa" + i);
        set(telekomPay.passwordTextF, "svep");

        click(telekomPay.loginBtn);

        //Place Order
        reportMessage(confirmOrder.BruttoValue.getText());
        reportMessage(confirmOrder.RecurringValue.getText());
        click(confirmOrder.placeOrderBtn);

        //Goto AppPage
        click(orderReceipt.goToMyApps);
    }
}
