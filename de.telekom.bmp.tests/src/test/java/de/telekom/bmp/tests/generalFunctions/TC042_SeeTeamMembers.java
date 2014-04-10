
package de.telekom.bmp.tests.generalFunctions;


import de.telekom.bmp.data.Datapool;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.account.Dashboard;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.reporting.Reporter.reportMessage;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.is;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */

@UseWebDriver
@QCId("3709")

public class TC042_SeeTeamMembers {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Datapool datapool;
    
    @Inject 
    Header header;
       
    @Inject 
    Dashboard dashboard;
        
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

        
       navigateTo(dashboard);
       int teamMembers = 0;
       
       for (WebElement member: dashboard.allMembers) {
           teamMembers++;
           reportMessage(member.getText());
       }
       
       verifyThat(teamMembers, is(5));
              
    }
}
