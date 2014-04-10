/**
 * 
 */
package de.telekom.bmp.functional;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static de.telekom.testframework.Assert.waitFor;
import static de.telekom.testframework.selenium.Matchers.displayed;
import static org.hamcrest.Matchers.is;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.openqa.selenium.Keys;

import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.superuser.DashboardPage;

/**
 * This class gives you some methods to assign roles to users
 * 
 * @author Mathias Herkt
 *
 */
public class AssignRoles {
	@Inject
	FunctionalActions fa;
	
	@Inject
	Header headPg;
	
	@Inject
	DashboardPage suDashboardPg;
	
	/**
	 * Assign the super user role to a given user
	 * @param superUser is a super user who as the rights to assign a super user
	 * @param newSuUser user which should get super user rights
	 */
	public void assignSuperuser(User superUser, User newSuUser) {
		fa.login(superUser);
		click(headPg.settingsMenu.superuserLnk);
		click(suDashboardPg.userLnk);
		
		click(suDashboardPg.smallSearchInput);
		set(suDashboardPg.smallSearchInput, newSuUser.email);
		set(suDashboardPg.smallSearchInput, Keys.RETURN);
		waitFor(2, TimeUnit.SECONDS);
		assertThat(suDashboardPg.userLnk, is(displayed()));
		
		click(suDashboardPg.superUserChkBox);
		newSuUser.role = UserRole.SUPERUSER;
		fa.logout();
	}

}
