/**
 * 
 */
package de.telekom.bmp.functional;

import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.openqa.selenium.Keys;

import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.superuser.DashboardPage;
import de.telekom.testframework.reporting.Reporter;

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

	/**
	 * Assign the channel admin role to a given user
	 * @param superUser is a super user who as the rights to assign a super user 
	 * @param userForChannelAdmin user which should get channel admin rights
	 */
	public void assignChannelAdmin(User superUser, User userForChannelAdmin) {
		fa.login(superUser.email, superUser.password);
		click(headPg.settingsMenu.superuserLnk);

		click(suDashboardPg.companyLnk);
		assertThat(suDashboardPg.smallSearchInput, is(displayed()));

		click(suDashboardPg.smallSearchInput);
		set(suDashboardPg.smallSearchInput, userForChannelAdmin.companyName);
		Reporter.reportMessage("Send Return to small search text field");
		set(suDashboardPg.smallSearchInput, Keys.RETURN);
		
		assertThat(suDashboardPg.channelAdminChkBox, is(not(selected())));
		click(suDashboardPg.channelAdminChkBox);
		click(suDashboardPg.userLnk);

		click(suDashboardPg.smallSearchInput);
		set(suDashboardPg.smallSearchInput, userForChannelAdmin.email);
		Reporter.reportMessage("Send Return to small search text field");
		set(suDashboardPg.smallSearchInput,Keys.RETURN);
		
		assertThat(suDashboardPg.userChannelAdminChkBox, is(displayed()));
		assertThat(suDashboardPg.userChannelAdminChkBox, is(not(selected())));
		click(suDashboardPg.userChannelAdminChkBox);
		userForChannelAdmin.role = UserRole.CHANNELADMIN;
		fa.logout();
	}

}
