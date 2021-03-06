
package de.telekom.bmp.tests.generalFunctions;


import de.telekom.bmp.data.Datapool;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.account.Dashboard;
import de.telekom.bmp.pages.settings.Directories;

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
@QCId("3710")
public class TC043_SeeAllTeamMembers {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Datapool datapool;
    
          
    @Inject 
    Dashboard dashboard;
    
    @Inject 
    Directories directories;
    
    
        
    @Test
    public void test1() throws InterruptedException {
        
//       functional.login("sysadmin4.0@test.com", "password");
//       
//       //createOrder.navigateTo("/1262");
//             
//
////        User user = datapool.users()             
////                .field("valid").equal(true)
////                .field("registered").equal(true)
////                .field("role").equal(UserRole.USER).get();
////
////        assertThat("we have a valid user", user != null);
////
////        functional.login(user.email, user.password);
//
//        
//       navigateTo(dashboard);
//       click(dashboard.allMembersLnk);
//       
//       verifyThat(directories.usersTable.isDisplayed());
//       int teamMembers = 0;
//       
//       for (WebElement member: directories.teamMembers) {
//           teamMembers++;
//           reportMessage(member.getText());
//       }
//       
//       verifyThat(teamMembers, is(8));
//              
    }
}
