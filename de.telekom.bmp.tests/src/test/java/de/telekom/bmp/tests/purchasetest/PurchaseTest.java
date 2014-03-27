
package de.telekom.bmp.tests.purchasetest;

import de.telekom.bmp.pages.purchase.BillingDetails;
import de.telekom.bmp.pages.purchase.CreateOrder;

import de.telekom.bmp.pages.playground.FunctionalActions;
import de.telekom.bmp.pages.purchase.CreateOrder.EditionRow;

import static de.telekom.testframework.Actions.*;

import static de.telekom.testframework.reporting.Reporter.reportMessage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */

@UseWebDriver
public class PurchaseTest {
    
    @Inject
    FunctionalActions functional;
    
    
    @Inject 
    CreateOrder createOrder;
    
    @Inject 
    BillingDetails billingDetails;
    
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("dtagtester01+playground0.0@googlemail.com", "baum1301");
       
       createOrder.navigateTo("/1262");
       
       
//       for (Link link: portal.categories ) {
//           reportMessage(link.getAttribute("title"));
//       }
       for (EditionRow edition: createOrder.editions) {
           //reportMessage(button.   ..getAttribute("title"));
           //reportMessage(edition.name.getText());
           if (edition.name.getText().contains("Small")) {
           reportMessage(edition.name.getText());
           //edition.name.click();
           click(edition.name);
           }
           
       }
       
       
       
       //createOrder.editions.get("Small").click();
       //createOrder.edition.get("Medium").click();
       //createOrder.edition.get("Trial").click();
       
      // createOrder.discountCode.set("vouchercode");
      // createOrder.applyDiscount.click();
       
       click(createOrder.nextStep);
       
       set(billingDetails.salutation, "COMPANY");
       
       Thread.sleep(2000);
       
       set(billingDetails.company, "Autom Company");
       set(billingDetails.lastName, "Autom User");
       set(billingDetails.address, "Telekom");
       set(billingDetails.city, "Darmstadt");
       set(billingDetails.postCode, "64295");
       set(billingDetails.country, "DE");
       set(billingDetails.state, "DE-HE");
       click(billingDetails.goBack);
       
        Thread.sleep(2000);
            
       System.out.println();
    }
}
