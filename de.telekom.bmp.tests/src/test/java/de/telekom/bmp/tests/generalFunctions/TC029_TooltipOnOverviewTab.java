
package de.telekom.bmp.tests.generalFunctions;


import de.telekom.bmp.data.Datapool;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.MyApps;

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
@QCId("3699")
public class TC029_TooltipOnOverviewTab {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Datapool datapool;
    
    @Inject 
    Header header;
       
    @Inject 
    MyApps launchpad;
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       //createOrder.navigateTo("/1262");
             

//        User user = datapool.users()             
//                .field("valid").equal(true)
//                .field("registered").equal(true)
//                .field("role").equal(UserRole.USER).get();
//
//        assertThat("we have a valid user", user != null);
//
//        functional.login(user.email, user.password);

        
       navigateTo(launchpad);
       click(launchpad.helpBtn);
       verifyThat(launchpad.helpText.isDisplayed());
       String s = "Über diese Seite haben Sie Zugang zu all Ihren Applikationen. Klicken Sie einfach auf eine der Applikationen, um auf diese zuzugreifen.";
       verifyThat(launchpad.helpText.getText(), is(s));
       click(launchpad.closeHelpBtn);
       click(launchpad.helpBtn);
       verifyThat(launchpad.helpText.isDisplayed());
       functional.logout();

       
    }
}
