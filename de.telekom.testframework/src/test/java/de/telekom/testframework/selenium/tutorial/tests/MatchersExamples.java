package de.telekom.testframework.selenium.tutorial.tests;

import static de.telekom.testframework.Actions.*;

import static de.telekom.testframework.selenium.Matchers.*;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.tutorial.application.pages.Home;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@UseWebDriver
public class MatchersExamples {

    @Inject
    Home home;

    @BeforeMethod
    public void before() {
        navigateTo(home);
    }

    @Test(enabled = false)
    public void existsExample() {
        verifyThat(home.username, exists());
        verifyThat(home.username, exists(is(true)));

        verifyThat(home.notExistingText, not(exists()));
        verifyThat(home.notExistingText, not(exists()));
    }

    @Test
    public void displayedExample() {
        verifyThat(home.username, is(displayed()));
        verifyThat(home.hiddenText, is(not(displayed())));
    }

    @Test
    public void enabledExample() {
        verifyThat(home.username, is(enabled()));
        verifyThat(home.disabledText, is(not(enabled())));
    }

    @Test
    public void valueExample() {
        verifyThat(home.username, value(isEmptyOrNullString()));
        set(home.username, "hallo");
        verifyThat(home.username, value(is("hallo")));
        verifyThat(home.username, value(is(not("bernd"))));
    }

    @Test
    public void tagnameExample() {
        verifyThat(home.username, tagName(is("input")));
        verifyThat(home.username, tagName(is(not("div"))));
    }

    @Test
    public void attributeExample() {
        verifyThat(home.username, attribute("value", isEmptyOrNullString()));
        set(home.username, "hallo");
        verifyThat(home.username, attribute("value", is("hallo")));
        verifyThat(home.username, attribute("value", is(not("bernd"))));
    }

    @Test
    public void loadedExample() {
        verifyThat(home, is(loaded()));
        verifyThat(home, loaded(is(true)));
    }
}
