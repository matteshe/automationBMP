
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.account.Account;
import de.telekom.bmp.pages.account.Dashboard;
import de.telekom.bmp.pages.account.Users;

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
public class TC009_ApplikationenZuweisen {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Header header;
       
    @Inject 
    Account account;
    
    @Inject
    Dashboard dashboard;
    
    @Inject
    Users users;
        
    @Test
    public void test1() throws InterruptedException {
        
        
        
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(dashboard);
       verifyThat(dashboard.isCurrentPage());
              
       System.out.println(account.getWebDriver().getCurrentUrl());
       verifyThat( dashboard.loadMoreBtn.isDisplayed());
       click(dashboard.loadMoreBtn);
       
       verifyThat(dashboard.inviteUsersLnk.isDisplayed());
       click(dashboard.inviteUsersLnk);
       verifyThat(dashboard.invitePopup.isDisplayed());
       
       verifyThat(dashboard.assignLnk.isDisplayed());
       click(dashboard.assignLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/assign")); 
       navigateTo(dashboard);
       
       verifyThat(dashboard.upgradeLnk.isDisplayed());
       click(dashboard.upgradeLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/apps")); 
       navigateTo(dashboard);
       
       verifyThat(dashboard.billingInfoLnk.isDisplayed());
       click(dashboard.billingInfoLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString("selectedPanel=BILLING_INFO")); 
       navigateTo(dashboard);
       
       verifyThat(dashboard.billsLnk.isDisplayed());
       click(dashboard.billsLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/bills")); 
       navigateTo(dashboard);
       
       verifyThat(dashboard.singleInviteBtn.isDisplayed());
       click(dashboard.allMembersLnk);
       verifyThat(dashboard.getWebDriver().getCurrentUrl(), containsString("/directories")); 
       
       
       functional.logout();

       
    }
}
