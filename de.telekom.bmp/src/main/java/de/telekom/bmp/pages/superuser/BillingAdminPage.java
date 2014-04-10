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
@Path("/superuser/billingAdmin")
public class BillingAdminPage extends SubNavHeaderPage {

	@Inject
	public BillingAdminPage(BmpApplication app) {
		super(app);
	}
}
