package de.telekom.bmp.pages.superuser.old;

import javax.inject.Singleton;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.annotations.Path;

/**
 * 
 * @author Mathias Herkt
 */
@Singleton
@Path("/superuser/reportProcess")
@Deprecated
public class RebuildIndexPage extends SubNavHeaderPage {

	@Inject
	public RebuildIndexPage(BmpApplication app) {
		super(app);
	}
}
