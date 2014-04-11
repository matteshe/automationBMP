/**
 * 
 */
package de.telekom.bmp.functional;

import static de.telekom.testframework.Actions.click;
import static de.telekom.testframework.Actions.set;
import static de.telekom.testframework.Assert.assertThat;
import static de.telekom.testframework.Assert.waitFor;
import static de.telekom.testframework.selenium.Matchers.displayed;
import static de.telekom.testframework.selenium.Matchers.selected;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.openqa.selenium.Keys;

import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.channel.CompanyPage;
import de.telekom.bmp.pages.channel.MarketPlacePage;
import de.telekom.bmp.pages.channel.UserPage;
import de.telekom.bmp.pages.superuser.DashboardPage;
import de.telekom.testframework.Actions;
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
	
	@Inject
	CompanyPage chCompPg;
	
	@Inject
	UserPage chUserPg;
	
	@Inject
	MarketPlacePage mpPg;
	
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

	/**
	 * Assign the sales support (SSR) role to a given user
	 * @param superUser is a super user who as the rights to assign a ssr role
	 * @param userForSrr user which should get ssr role
	 */
	public void assignSsrRoleViaSuperUser(User superUser, User userForSrr) {
		fa.login(superUser.email, superUser.password);
		click(headPg.settingsMenu.channelUserLnk);
		click(mpPg.customerLnk);

		assertThat(mpPg.smallSearchInput, is(displayed()));
		click(mpPg.smallSearchInput);
		set(mpPg.smallSearchInput, userForSrr.email);
		set(mpPg.smallSearchInput, Keys.RETURN);
		waitFor(2, TimeUnit.SECONDS);
		
		// choose user from list
		mpPg.userInListLnk.click();
		assertThat(chUserPg.customerDataLnk, is(displayed()));
		click(chUserPg.customerDataLnk);
		setCompanyChannelAdmin();
		
		Actions.click(chCompPg.userLnk.get(userForSrr.email));
		setUserChannelAdmin();
		
		userForSrr.role = UserRole.SSR;
		fa.logout();
	}
	
	private void setCompanyChannelAdmin() {
		assertThat(chCompPg.channelAdminChkbox, is(displayed()));
		assertThat(chCompPg.channelAdminChkbox, is(not(selected())));
		//chCompPg.channelAdminChkbox.select();
		click(chCompPg.channelAdminChkbox);
		assertThat(chCompPg.feedbackPanelINFO, is(displayed()));
	}
	
	private void setUserChannelAdmin() {
		assertThat(chUserPg.salesSupportChkbox, is(displayed()));
		//chUserPg.salesSupportChkbox.select();
		click(chUserPg.salesSupportChkbox);
		assertThat(chUserPg.feedbackPanelINFO, is(displayed()));
	}
}
