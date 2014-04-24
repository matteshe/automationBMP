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
@Path("/superuser/rebuildindex")
@Deprecated
public class ReportProcessPage extends SubNavHeaderPage {

	@Inject
	public ReportProcessPage(BmpApplication app) {
		super(app);
	}
}
