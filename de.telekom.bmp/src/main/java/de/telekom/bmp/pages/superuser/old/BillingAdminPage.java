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
@Path("/superuser/billingAdmin")
@Deprecated
public class BillingAdminPage extends SubNavHeaderPage {

	@Inject
	public BillingAdminPage(BmpApplication app) {
		super(app);
	}
}
