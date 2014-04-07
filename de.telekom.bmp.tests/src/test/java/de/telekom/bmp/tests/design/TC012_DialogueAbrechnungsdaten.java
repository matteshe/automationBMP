
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.account.Bills;

import static de.telekom.testframework.Actions.*;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */

@UseWebDriver
public class TC012_DialogueAbrechnungsdaten {
    
    @Inject
    FunctionalActions functional;
            
    @Inject
    Bills bills;
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(bills);
       click(bills.paymentDetails);
       
       verifyThat(bills.title.isDisplayed());
       //System.out.println(bills.title);
       //verifyThat(bills.title.getAttribute("required"), is(null));
       verifyThat(bills.companyInput.isDisplayed());
       verifyThat(bills.nameInput.isDisplayed());
       verifyThat(bills.lastnameInput.isDisplayed());
       verifyThat(bills.addressInput.isDisplayed());
       verifyThat(bills.cityInput.isDisplayed());
       verifyThat(bills.countries.size(), is(greaterThanOrEqualTo(1)));
       verifyThat(bills.states.size(), is(greaterThanOrEqualTo(1)));
       verifyThat(bills.vatInput.isDisplayed());
       
    }
}
