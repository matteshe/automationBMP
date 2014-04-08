
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.account.Account;
import de.telekom.bmp.pages.account.InvitePopup;
import de.telekom.bmp.pages.account.Roles;
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
public class TC008_DialogueApplikationVerwalten {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Header header;
       
    @Inject 
    Account account;
    
    @Inject
    Users users;
    
    @Inject
    Roles roles;
    
    @Inject
    InvitePopup invitePopup;
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(users);
       verifyThat(users.isCurrentPage());
              
       verifyThat(users.inviteUsersBtn.isDisplayed());
       verifyThat(users.manageRolesBtn.isDisplayed());
       verifyThat(users.searchInput.isDisplayed());  
       verifyThat(users.usersTable.isDisplayed());
       
       click(users.inviteUsersBtn);
       verifyThat(invitePopup.invitePopup.isDisplayed());
       
       System.out.println("Number of email Fields is " + invitePopup.invitationFields.size());
       verifyThat(invitePopup.invitationFields.size(), is(5));
       
       verifyThat(invitePopup.addAnotherEmailLnk.isDisplayed());
       verifyThat(invitePopup.sendInvitationsBtn.isDisplayed());
       
       navigateTo(users);
       click(users.manageRolesBtn);
       verifyThat(users.getWebDriver().getCurrentUrl(), containsString(account.getPath() + "/roles")); 
       verifyThat(roles.usersTable.isDisplayed());
       verifyThat(roles.assignRolesTable.isDisplayed());
       verifyThat(roles.searchInput.isDisplayed());
                    
       functional.logout();

       
    }
}
