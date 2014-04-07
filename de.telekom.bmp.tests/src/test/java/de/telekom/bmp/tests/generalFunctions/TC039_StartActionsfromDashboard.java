
package de.telekom.bmp.tests.generalFunctions;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.account.Account;
import de.telekom.bmp.pages.account.Dashboard;
import de.telekom.bmp.pages.account.InvitePopup;

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
public class TC039_StartActionsfromDashboard {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Header header;
       
    @Inject 
    Account account;
    
    @Inject
    Dashboard dashboard;
    
    @Inject 
    InvitePopup invitePopup;
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(dashboard);
       verifyThat(dashboard.isCurrentPage());
              
       click(dashboard.inviteUsersLnk);
       verifyThat(invitePopup.invitePopup.isDisplayed());
       
       click(dashboard.assignLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/assign")); 
       navigateTo(dashboard);
       
       click(dashboard.upgradeLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/apps")); 
       navigateTo(dashboard);
       
       click(dashboard.billingInfoLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString("selectedPanel=BILLING_INFO")); 
       navigateTo(dashboard);
       
       click(dashboard.billsLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/bills")); 
       navigateTo(dashboard);
              
       functional.logout();

       
    }
}