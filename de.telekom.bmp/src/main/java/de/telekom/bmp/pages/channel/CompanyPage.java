/**
 * 
 */
package de.telekom.bmp.pages.channel;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
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
@Path("/channel/company")
public class CompanyPage extends Page {

	@Inject
	public CompanyPage(BmpApplication application) {
		super(application);
	}

	@FindBy(xpath = "//div[@class = 'csrDetail-top company']")
	private Control csrDetailTopCompanyParent;

	@FindBy(xpath = "//input[contains(@name,'vendorCheckbox')]")
	@UseParent("csrDetailTopCompanyParent")
	public Option vendorCheckbox;

	@FindBy(xpath = "//input[contains(@name,'channelAdminCheckbox')]")
	@UseParent("csrDetailTopCompanyParent")
	public Option channelAdminChkbox;

	@FindBy(xpath = "//input[contains(@name,'ssrGranting')]")
	@UseParent("csrDetailTopCompanyParent")
	public Option ssrGrantingChkbox;

	@FindBy(xpath = "//input[contains(@name,'rssrGranting')]")
	@UseParent("csrDetailTopCompanyParent")
	public Option rssrGrantingChkbox;

	@FindBy(xpath = "//span[@class='feedbackPanelINFO']")
	public Text feedbackPanelINFO;

	@FindBy(xpath = "//table[@class='item-table billsTable']//span[text() = '%s']")
	public Parameterized<Link> userLnk;
}
