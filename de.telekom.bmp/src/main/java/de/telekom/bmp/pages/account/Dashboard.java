package de.telekom.bmp.pages.account;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.pages.Header.AccountLink;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Form;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.controls.TextField;

/**
 *
 * @author Tatiana
 */

@Singleton
@Path("/account/home")
public class Dashboard extends Page {

    @Inject
    public Dashboard(BmpApplication app) {
        super(app);
    } 
    
    @FindBy(xpath = "//a[@id='logout' or text()='Abmelden']")
    public AccountLink logoutLnk;
    
    @FindBy(xpath = ".//div[@id='subnav-header']")
    Link subNavHeader;
    
    @FindBy(xpath = ".//a[contains(@href, 'home')]")
    @UseParent("subNavHeader")
    public Link dashboardLnk;
    
    @FindBy(xpath = ".//a[contains(@href, 'users')]")
    @UseParent("subNavHeader")
    public Link usersLnk;
    
    @FindBy(xpath = ".//button[contains(@id, 'everyone')]")
    public Button everyoneBtn;
    
    @FindBy(xpath = ".//button[contains(@id, 'justMe')]")
    public Button justMeBtn;
    
    @FindBy(xpath = ".//div[contains(@class, 'activity_section')]")
    public Form activities;
    
    @FindBy(xpath = ".//a[contains(@class, 'loadMore')]/span")
    public Button loadMoreBtn;
    
    @FindBy(xpath = ".//div[@id='quickLinks']")
    Link quickLinks;
    
    @FindBy(xpath = ".//a[@class='inviteMore']")
    @UseParent("quickLinks")
    public Link inviteUsersLnk;
        
    @FindBy(xpath = ".//a[contains(@href, 'assign')]")
    @UseParent("quickLinks")
    public Link assignLnk;
    
    @FindBy(xpath = ".//a[contains(@href, 'apps')]")
    @UseParent("quickLinks")
    public Link upgradeLnk;
    
    @FindBy(xpath = ".//a[contains(@href, 'bills?selectedPanel=BILLING_INFO')]")
    @UseParent("quickLinks")
    public Link billingInfoLnk;
    
    @FindBy(xpath = ".//a[@href='./bills']")
    @UseParent("quickLinks")
    public Link billsLnk;
    
    @FindBy(xpath = ".//div[@id='singleUserInvite']")
    Form singleInvite;
    
    @FindBy(xpath = ".//button[@name=':submit']")
    @UseParent("singleInvite")
    public Button singleInviteBtn;
    
    @FindBy(xpath = ".//div[contains(@class, 'headerTop')]//a[contains(@href, '/directories/')]")
    public Link allMembersLnk;
    
    @FindBy(xpath = ".//div[contains(@class, 'account-cont teambox')]//div[contains(@class, 'teammember')]")
    public List<Link> allMembers;
    
    // Invite popup:
    @FindBy(xpath = ".//form[@class = 'addCoWorkersForm']")
    public Form invitePopup;
    
    @FindBy(name = "email")
    public TextField inviteEmailInput;
    
    @FindBy(xpath = "//span[@class='feedbackPanelINFO']")
    public Text inviteSuccessfullTxt;
    
}
