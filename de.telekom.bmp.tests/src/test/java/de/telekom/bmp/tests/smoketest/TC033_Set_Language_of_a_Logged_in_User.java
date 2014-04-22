package de.telekom.bmp.tests.smoketest;

import de.telekom.bmp.pages.*;
import static de.telekom.testframework.Actions.click;

import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Assert.checkThat;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.Assert.waitUntil;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import static de.telekom.testframework.selenium.Matchers.innerHTML;
import static de.telekom.testframework.selenium.Matchers.loaded;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@QCId(value = "5594", state = QCState.ReadyButNeedsReview)
@UseWebDriver
public class TC033_Set_Language_of_a_Logged_in_User {

    @Inject
    Login login;

    @Inject
    Header header;

    @Test
    public void theTest() throws InterruptedException {
        navigateTo(login);
        waitUntil(login, is(loaded()));

        // if "deutsch" is current language, first switch to another language then "deutsch"
        if (checkThat(header.currentLanguage, innerHTML(containsString("deutsch")))) {
            click(header.toggleLanguage);
            waitUntil(login, is(loaded()));
        }

        // toggle the language till "deutsch" is the current language
        while (!checkThat(header.currentLanguage, innerHTML(containsString("deutsch")))) {
            click(header.toggleLanguage);
            waitUntil(login, is(loaded()));
        }

        verifyThat(header.currentLanguage, innerHTML(containsString("deutsch")));

        // TODO further verifications needed?
    }

}
