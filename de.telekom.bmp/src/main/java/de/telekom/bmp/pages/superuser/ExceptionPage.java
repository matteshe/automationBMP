package de.telekom.bmp.pages.superuser;

import javax.inject.Singleton;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.annotations.Path;

/**
 * 
 * @author Mathias Herkt
 */
@Singleton
@Path("/superuser/exceptions")
public class ExceptionPage extends SubNavHeaderPage {

	@Inject
	public ExceptionPage(BmpApplication app) {
		super(app);
	}
}
