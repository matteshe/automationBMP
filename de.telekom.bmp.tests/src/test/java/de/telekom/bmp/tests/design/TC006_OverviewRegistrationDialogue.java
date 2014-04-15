
package de.telekom.bmp.tests.design;

import de.telekom.bmp.pages.Signup;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
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
       verifyThat(registration.signupForm.emailAddress.isDisplayed()); 
              
       verifyThat(registration.signupForm.signup.isDisplayed());
       verifyThat(registration.alreadyRegistered.isDisplayed());
       
    }
}
