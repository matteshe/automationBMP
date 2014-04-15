package de.telekom.googlemail.pages;

import de.telekom.googlemail.GoogleMailApplication;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Link;

/**
 * 
 * @author Mathias Herkt
 */
@Singleton
@Path("/mail/?ui=html&zy=h")
public class OldMail extends Page {

	@Inject
	public OldMail(GoogleMailApplication app) {
		super(app);
	}

	@FindBy(xpath = "//span[@class='ts']")
	public List<Link> emailLinks;

	@FindBy(partialLinkText = "%s")
	public Parameterized<Link> emailLnk;

	@FindBy(xpath = "//div[@class='msg']//a")
	public List<Link> linksInMessage;

	@FindBy(xpath = "//div[@class='msg']//a[contains(@href, '%s')]")
	public Parameterized<Link> confirmLink;
}
