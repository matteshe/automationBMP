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
import de.telekom.bmp.pages.channel.CompanyPage;
import de.telekom.bmp.pages.channel.MarketPlacePage;
import de.telekom.bmp.pages.channel.UserPage;
import de.telekom.bmp.pages.superuser.old.DashboardOld;
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
	DashboardOld suDashboardPg;
	
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
		click(headPg.settings.superUser);
		click(suDashboardPg.userLnk);
		
		click(suDashboardPg.smallSearchInput);
		set(suDashboardPg.smallSearchInput, newSuUser.email);
                sendKeys(suDashboardPg.smallSearchInput, Keys.RETURN);
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
		click(headPg.settings.superUser);

		click(suDashboardPg.companyLnk);
		assertThat(suDashboardPg.smallSearchInput, is(displayed()));

		click(suDashboardPg.smallSearchInput);
		set(suDashboardPg.smallSearchInput, userForChannelAdmin.companyName);
		Reporter.reportMessage("Send Return to small search text field");
		sendKeys(suDashboardPg.smallSearchInput, Keys.RETURN);
		
		assertThat(suDashboardPg.channelAdminChkBox, is(not(selected())));
		click(suDashboardPg.channelAdminChkBox);
		click(suDashboardPg.userLnk);

		click(suDashboardPg.smallSearchInput);
		set(suDashboardPg.smallSearchInput, userForChannelAdmin.email);
		Reporter.reportMessage("Send Return to small search text field");
		sendKeys(suDashboardPg.smallSearchInput,Keys.RETURN);
		
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
		click(headPg.settings.channelUser);
		click(mpPg.customerLnk);

		assertThat(mpPg.smallSearchInput, is(displayed()));
		click(mpPg.smallSearchInput);
		set(mpPg.smallSearchInput, userForSrr.email);
		sendKeys(mpPg.smallSearchInput, Keys.RETURN);
		waitFor(2, TimeUnit.SECONDS);
		
		// choose user from list
		click(mpPg.userInListLnk);
		assertThat(chUserPg.customerDataLnk, is(displayed()));
		click(chUserPg.customerDataLnk);
		
		setCompanyChannelAdmin();
		
		Actions.click(chCompPg.userLnk.get(userForSrr.email));
		
		setUserSsr();
		
		userForSrr.role = UserRole.SSR;
		fa.logout();
	}
	
	/**
	 * Assign the restrictive sales support (RSSR) role to a given user
	 * @param superUser is a super user who as the rights to assign a rssr role
	 * @param userForSrr user which should get rssr role
	 */
	public void assignRssrRoleViaSuperUser(User superUser, User userForSrr) {
		fa.login(superUser.email, superUser.password);
		click(headPg.settings.channelUser);
		click(mpPg.customerLnk);

		assertThat(mpPg.smallSearchInput, is(displayed()));
		click(mpPg.smallSearchInput);
		set(mpPg.smallSearchInput, userForSrr.email);
		sendKeys(mpPg.smallSearchInput, Keys.RETURN);
		waitFor(2, TimeUnit.SECONDS);
		
		// choose user from list
		click(mpPg.userInListLnk);
		assertThat(chUserPg.customerDataLnk, is(displayed()));
		click(chUserPg.customerDataLnk);

		setCompanyChannelAdmin();
		
		click(chCompPg.userLnk.get(userForSrr.email));
		
		setUserRssr();
		
		userForSrr.role = UserRole.RSSR;
		fa.logout();
	}
	
	private void setCompanyChannelAdmin() {
		assertThat(chCompPg.channelAdminChkbox, is(displayed()));
		
		// TODO if case instead of assert check
		assertThat(chCompPg.channelAdminChkbox, is(not(selected())));
		click(chCompPg.channelAdminChkbox);
		assertThat(chCompPg.feedbackPanelINFO, is(displayed()));
	}
	
	private void setUserSsr() {
		assertThat(chUserPg.salesSupportChkbox, is(displayed()));
		// TODO if case instead of assert check
		assertThat(chUserPg.salesSupportChkbox, is(not(selected())));
		click(chUserPg.salesSupportChkbox);
		assertThat(chUserPg.feedbackPanelINFO, is(displayed()));
	}
	
	private void setUserRssr() {
		assertThat(chUserPg.restrictedSalesSupportChkbox, is(displayed()));
		// TODO if case instead of assert check
		assertThat(chUserPg.restrictedSalesSupportChkbox, is(not(selected())));
		click(chUserPg.restrictedSalesSupportChkbox);
		assertThat(chUserPg.feedbackPanelINFO, is(displayed()));
	}

	/**
	 * Remove restrictive sales support (RSSR) role from a given user
	 * @param channelAdminUser is a user who as the rights to remove the rssr role
	 * @param rssrUser user which should get rssr role
	 */
	public void removeRssrRole(User channelAdminUser, User rssrUser) {
		// TODO 20140411 must be implemented
		assertThat("not yet implemented", false);
	}
	
	/**
	 * Remove sales support (SSR) role from a given user
	 * @param channelAdminUser is a user who as the rights to remove the ssr role
	 * @param rssrUser user which should get ssr role
	 */
	public void removeSsrRole(User channelAdminUser, User ssrUser) {
		// TODO 20140411 must be implemented
		assertThat("not yet implemented", false);
	}
}
