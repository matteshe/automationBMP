package de.telekom.bmp.pages.account;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */

@Singleton
@Path("/account")
public class Account extends Page {

    @Inject
    public Account(BmpApplication app) {
        super(app);
    } 
    
    @FindBy(xpath = ".//div[@id='subnav-header']")
    Link subNavHeader;
    
    @FindBy(xpath = ".//a[contains(@href, 'home')]")
    @UseParent("subNavHeader")
    public Link dashboardLnk;
    
    @FindBy(xpath = ".//a[contains(@href, 'users')]")
    @UseParent("subNavHeader")
    public Link usersLnk;
    
    @FindBy(xpath = ".//a[contains(@href, 'apps')]")
    @UseParent("subNavHeader")
    public Link applicationsLnk;
    
    @FindBy(xpath = ".//a[contains(@href, 'assign')]")
    @UseParent("subNavHeader")
    public Link assignLnk;
    
    @FindBy(xpath = ".//a[contains(@href, 'bills')]")
    @UseParent("subNavHeader")
    public Link billsLnk;
    
    @FindBy(xpath = ".//a[contains(@href, 'companySettings')]")
    @UseParent("subNavHeader")
    public Link companySettingsLnk;
    
       
}
