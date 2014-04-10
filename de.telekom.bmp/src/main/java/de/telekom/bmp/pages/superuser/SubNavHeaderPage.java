/**
 * 
 */
package de.telekom.bmp.pages.superuser;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Link;

/**
 * @author Mathias Herkt
 *
 */
@Singleton
public abstract class SubNavHeaderPage extends Page {
	
	@Inject
	public SubNavHeaderPage(BmpApplication application) {
		super(application);
	}

	@FindBy(xpath = "//div[@id = 'subnav-header']//a[contains(text(),'Übersicht')]")
	public Link overviewTab;
	
	@FindBy(xpath = "//div[@id = 'subnav-header']//a[contains(text(),'Ausnahmen')]")
	public Link exceptionsTab;
	
	@FindBy(xpath = "//div[@id = 'subnav-header']//a[contains(text(),'Billing Admin')]")
	public Link billingAdminTab;
	
	@FindBy(xpath = "//div[@id = 'subnav-header']//a[contains(text(),'Bills')]")
	public Link billsTab;
	
	@FindBy(xpath = "//div[@id = 'subnav-header']//a[contains(text(),'Reports')]")
	public Link reportsTab;
	
	@FindBy(xpath = "//div[@id = 'subnav-header']//a[contains(text(),'Index')]")
	public Link indexTab;
	
	@FindBy(xpath = "//div[@id = 'subnav-header']//a[contains(text(),'Shopeinstellungen')]")
	public Link shopSettingsTab;
}
