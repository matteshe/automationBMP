package de.telekom.bmp.tests.smoketest;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.pages.Home;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.annotations.QCState.Ready;
import static de.telekom.testframework.selenium.Matchers.value;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

/**
 * @author Mathias Herkt
 *
 */
@Deprecated
@UseWebDriver
@QCId(value = "5509", state = Ready)
public class TC001_Verify_Release_version_in_Sourcecode {

    @Inject
    BmpApplication app;

    @Inject
    Home homePage;

    @Test
    @Deprecated
    public void theTest() {
        navigateTo(homePage);
        verifyThat("BMP Version: ", homePage.bodyFooterDescribe, value(is(app.configuration.version)));
    }
}
