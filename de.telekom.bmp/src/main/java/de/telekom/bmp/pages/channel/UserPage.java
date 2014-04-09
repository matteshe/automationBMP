/**
 * 
 */
package de.telekom.bmp.pages.channel;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.telekom.testframework.selenium.Application;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Link;

/**
 * @author Mathias Herkt
 * 
 */
@Singleton
@Path("/channel/user")
public class UserPage extends Page {

	@Inject
	public UserPage(Application application) {
		super(application);
	}

	@FindBy(xpath = "//div[@class = 'csrDetail-top']")
	private WebElement csrDetailParent;

	@FindBy(xpath = "//a[text() = 'Unternehmensdaten']")
	@UseParent("csrDetailParent")
	public Link customerDataLnk;
}
