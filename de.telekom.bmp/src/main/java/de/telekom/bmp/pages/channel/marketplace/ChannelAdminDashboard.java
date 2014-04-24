package de.telekom.bmp.pages.channel.marketplace;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author Nomop
 */
@Singleton
@Path("/channel/marketplace")
public class ChannelAdminDashboard extends Page {

    @Inject
    public ChannelAdminDashboard(BmpApplication app) {
        super(app);
    }

    
}
