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
@Path("/channel/user")
public class UserPage extends Page {

	@Inject
	public UserPage(Application application) {
		super(application);
	}

}
