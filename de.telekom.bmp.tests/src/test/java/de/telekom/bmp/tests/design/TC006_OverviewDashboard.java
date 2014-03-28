
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.account.Account;
import de.telekom.bmp.pages.account.Dashboard;

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
public class TC006_OverviewDashboard {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Header header;
       
    @Inject 
    Account account;
    
    @Inject
    Dashboard dashboard;
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(dashboard);
       verifyThat(dashboard.isCurrentPage());
              
       System.out.println(account.getWebDriver().getCurrentUrl());
       verifyThat( dashboard.loadMoreBtn.isDisplayed());
       click( dashboard.loadMoreBtn);
       
       verifyThat( account.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/users")); 
       
       
       functional.logout();

       
    }
}
