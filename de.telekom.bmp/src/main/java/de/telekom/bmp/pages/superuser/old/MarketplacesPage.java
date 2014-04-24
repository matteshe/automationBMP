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
@Path("/superuser/marketplaces")
@Deprecated
public class MarketplacesPage extends SubNavHeaderPage {

	@Inject
	public MarketplacesPage(BmpApplication app) {
		super(app);
	}
}
