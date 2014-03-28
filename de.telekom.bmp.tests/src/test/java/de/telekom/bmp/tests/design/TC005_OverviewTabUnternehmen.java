
package de.telekom.bmp.tests.design;

import de.telekom.bmp.data.Datapool;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.account.Account;

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
public class TC005_OverviewTabUnternehmen {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Datapool datapool;
    
    @Inject 
    Header header;
       
    @Inject 
    Account account;
        
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

        
       moveTo(header.settingsMenu);
       click(header.accountLnk);
       
       System.out.println(account.getWebDriver().getCurrentUrl());
       verifyThat( account.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/home")); 
       
       click(account.usersLnk);
       verifyThat( account.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/users")); 
       
       click(account.applicationsLnk);
       verifyThat( account.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/apps")); 
       
       click(account.assignLnk);
       verifyThat( account.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/assign")); 
       
       click(account.usersLnk);
       verifyThat( account.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/user")); 
       
       click(account.billsLnk);
       verifyThat( account.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/bills")); 
       
       click(account.companySettingsLnk);
       verifyThat( account.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/companySettings")); 
       
       functional.logout();

       
    }
}
