package de.telekom.bmp.tests.playground;

import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.playground.Test1;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@QCId("1234")
@UseWebDriver
public class NewEmptyTestNGTest {

    @Inject
    WebDriver driver;

    @Inject
    Test1 test;

    @Inject
    Login login;

    @Test
    public void r1() {

        test.navigateTo();
        set(test.emailAddress, "abc");

        verifyThat(test.emailAddress, value(is("abc")));
        verifyThat(test.emailAddress, text(is(not("abc"))));

        verifyThat(test.emailAddress, isDisplayed());
        verifyThat(test.emailAddress, isDisplayed(is(true)));

        verifyThat(test.emailAddress, exists(is(true)));
        verifyThat(test.emailAddress, exists());

        verifyThat(test.notExisting, not(exists()));
        verifyThat(test.notExisting, exists(is(false)));
    }

}
