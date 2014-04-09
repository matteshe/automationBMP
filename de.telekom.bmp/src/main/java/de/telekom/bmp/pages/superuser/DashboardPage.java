package de.telekom.bmp.pages.superuser;

import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Option;
import de.telekom.testframework.selenium.controls.TextField;

/**
 * 
 * @author Daniel
 */
@Singleton
@Path("/superuser/dashboard")
public class DashboardPage extends Page {

	@Inject
	public DashboardPage(BmpApplication app) {
		super(app);
	}

	@FindBy(xpath = "//ul[@class='account-tabs']")
	public Container parent;

	@FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[2]/a")
	public Link exceptionsTab;

	@FindBy(xpath = "//ul[@class = 'account-tabs']//a[text()='Benutzer']")
	public Link userLnk;

	@FindBy(xpath = "//ul[@class='account-tabs']//a[text()='Unternehmen']")
	public Link companyLnk;

	@FindBy(xpath = "//ul[@class = 'account-tabs']//a[text()='Applikationen']")
	public Link applicationLnk;

	@FindBy(xpath = "//div[@class='smallSearch']//input")
	public TextField smallSearchInput;

	@FindBy(xpath = "//input[contains(@name, 'companyTable') and contains(@name, 'channelAdmin')]")
	public Option channelAdminChkBox;

	@FindBy(xpath = "//input[contains(@name, 'userTable') and contains(@name, 'channelAdmin')]")
	public Option userChannelAdminChkBox;

	@FindBy(xpath = "//input[contains(@name, 'userTable') and contains(@name, 'channelSupport')]")
	public Option userChannelSupportChkBox;

	@FindBy(xpath = "//button//span[text()='Übernehmen']")
	public Button submit;
}
