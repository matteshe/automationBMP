
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Signup;
import de.telekom.bmp.pages.account.Account;
import de.telekom.bmp.pages.account.Dashboard;
import de.telekom.bmp.pages.account.Users;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */

@UseWebDriver
@QCId("3547")

public class TC006_OverviewRegistrationDialogue {
    
    
    @Inject
    Signup registration;
        
    @Test
    public void test1() throws InterruptedException {
        
       
       navigateTo(registration);
       verifyThat(registration.isCurrentPage());
              
       verifyThat(registration.registerHeader.isDisplayed());
       verifyThat(registration.emailAddress.isDisplayed()); 
              
       verifyThat(registration.signup.isDisplayed());
       verifyThat(registration.alreadyRegistered.isDisplayed());
       
    }
}
