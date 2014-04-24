package de.telekom.bmp.pages.superuser.old;

import de.telekom.bmp.pages.superuser.old.SubNavHeaderPage;
import javax.inject.Singleton;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.annotations.Path;

/**
 * 
 * @author Mathias Herkt
 */
@Singleton
@Path("/superuser/bills")
@Deprecated
public class BillsPage extends SubNavHeaderPage {

	@Inject
	public BillsPage(BmpApplication app) {
		super(app);
	}
}
