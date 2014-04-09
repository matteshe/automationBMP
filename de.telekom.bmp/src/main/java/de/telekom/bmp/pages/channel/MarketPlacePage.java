package de.telekom.bmp.pages.channel;

import javax.inject.Singleton;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.TextField;

/**
 * 
 * @author Daniel
 */
@Singleton
public class MarketPlacePage extends Page {

	@Inject
	public MarketPlacePage(BmpApplication app) {
		super(app);
	}

	@Override
	public String getPath() {
		return "/channel/marketplace";
	}

	@Override
	public String toString() {
		return super.toString() + getPath();
	}

	@FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[2]/a")
	public Link productsTab;

	@FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[2]/a")
	public Link evenlogsTab;

	@FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[3]/a")
	public Link settingsTab;

	@FindBy(xpath = "//div[@class='pageHeaderButton']/a/button[@class='neutral fright']")
	public Button browseByCompBtn;

	@FindBy(xpath = "//div[@class='pageHeaderButton']/a/button[@class='neutral fright mr10']")
	public Button createNewCompBtn;

	// Create Company Form
	@FindBy(name = "companySetupForm:companyNameBorder:companyNameBorder_body:companyName")
	public TextField companyName;

	@FindBy(name = "companySetupForm:requiredPhoneNumber:requiredPhoneNumber_body:phoneNumber")
	public TextField phone;

	@FindBy(name = "firstName")
	public TextField testerFirstName;

	@FindBy(name = "lastName")
	public TextField testerLastName;

	@FindBy(name = "emailAddress")
	public TextField emailAdress;

	@FindBy(name = "dtCrmCustomerId")
	public TextField tdgCustomerNr;

	@FindBy(name = "marketingPanelContainer:dataPermissionCheckbox")
	public Button dataPermMarketingChBox;

	@FindBy(name = "marketingPanelContainer:emailMarketingCheckbox")
	public Button emailMarketingChBox;

	@FindBy(name = "companySetupForm:sizeRadioGroup")
	public Button companySizeRadio;

	@FindBy(xpath = "//div[@class='modal-footer']/button")
	public Button createFormCompBtn;

	@FindBy(xpath = "//div[@id='channelMenu']//a[text()='%s']")
	public Parameterized<Link> leftLinks;

	@FindBy(xpath = "//li[@class='feedbackPanelINFO']")
	public WebElement companyCreatedMsg;

	@FindBy(xpath = "//div[@id = 'channelMenu']//a[text() = 'Kunden']")
	public Link customerLnk;

	@FindBy(xpath = "//div[@class='smallSearch']//input")
	public TextField smallSearchInput;

	// @FindBy(xpath =
	// "//table[@class ='item-table billsTable']//span[text() = '%s'")
	//@FindBy(linkText = "%s")
	//@FindBy(xpath = "//tr[@id='id2e']/td[6]/span")
	@FindBy(xpath = "//table[@class ='item-table billsTable']//tr/td/span")
	public WebElement userInListLnk;
}
