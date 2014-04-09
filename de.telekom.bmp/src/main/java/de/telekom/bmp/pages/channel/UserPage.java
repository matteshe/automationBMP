/**
 * 
 */
package de.telekom.bmp.pages.channel;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Control;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Option;
import de.telekom.testframework.selenium.controls.Text;

/**
 * @author Mathias Herkt
 * 
 */
@Singleton
@Path("/channel/user")
public class UserPage extends Page {

	@Inject
	public UserPage(BmpApplication application) {
		super(application);
	}

	@FindBy(xpath = "//div[@class = 'csrDetail-top']")
	private WebElement csrDetailParent;

	@FindBy(xpath = "//a[text() = 'Unternehmensdaten']")
	@UseParent("csrDetailParent")
	public Link customerDataLnk;

	@FindBy(xpath = "//div[@class = 'csr-section']")
	private Control csrSectionParent;

	@FindBy(xpath = "//input[contains(@name,'billingAdmin')]")
	@UseParent("csrSectionParent")
	public Option billingAdminChkbox;

	@FindBy(xpath = "//input[contains(@name,'sysAdmin')]")
	@UseParent("csrSectionParent")
	public Option sysAdminChkbox;

	@FindBy(xpath = "//input[contains(@name,'tester')]")
	@UseParent("csrSectionParent")
	public Option testerChkbox;

	@FindBy(xpath = "//input[contains(@name,'channelProductSupport')]")
	@UseParent("csrSectionParent")
	public Option channelProductSupportChkbox;

	@FindBy(xpath = "//input[contains(@name,'salesSupport')]")
	@UseParent("csrSectionParent")
	public Option salesSupportChkbox;

	@FindBy(xpath = "//input[contains(@name,'restrictedSalesSupport')]")
	@UseParent("csrSectionParent")
	public Option restrictedSalesSupportChkbox;

	@FindBy(xpath = "//input[contains(@name,'channelSupport')]")
	@UseParent("csrSectionParent")
	public Option channelSupportChkbox;

	@FindBy(xpath = "//input[contains(@name,'channelAdmin')]")
	@UseParent("csrSectionParent")
	public Option channelAdminChkbox;

	@FindBy(xpath = "//span[@class='feedbackPanelINFO']")
	public Text feedbackPanelINFO;
}
