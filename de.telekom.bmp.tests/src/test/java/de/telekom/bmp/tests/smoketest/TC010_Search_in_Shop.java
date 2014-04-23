package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Application;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Listing;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Pierre Nomo, Daniel Biehl
 */
@UseWebDriver
@QCId(value = "5502", state = QCState.Ready)
public class TC010_Search_in_Shop {

    @Inject
    BmpApplication app;

    @Inject
    Header header;

    @Inject
    Listing listing;

    @Inject
    Datapool datapool;

    Application appInfo;

    @BeforeMethod
    public void preparation() {
        appInfo = datapool.applications().get();
        if (appInfo == null) {
            appInfo = datapool.helpers().getTestApplication();
        }
        navigateTo(app);
    }

    @Test
    public void theTest() {
        assertThat("we have a valid application", appInfo != null);

        header.searchInput.set(appInfo.name);
        header.searchBtn.click();

        assertThat(listing, is(currentPage()));

        verifyThat(listing.results.rowByName.get(appInfo.name), exists());
    }

}
