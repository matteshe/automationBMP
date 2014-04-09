/**
 * 
 */
package de.telekom.bmp.pages.channel;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.telekom.testframework.selenium.Application;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;

/**
 * @author Mathias Herkt
 * 
 */
@Singleton
@Path("/channel/company")
public class CompanyPage extends Page {

	@Inject
	public CompanyPage(Application application) {
		super(application);
	}
}
