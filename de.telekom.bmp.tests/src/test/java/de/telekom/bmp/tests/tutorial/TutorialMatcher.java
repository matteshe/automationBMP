package de.telekom.bmp.tests.playground.tutorial;


import de.telekom.bmp.pages.playground.PlayLogin;

import org.testng.annotations.Test;
import javax.inject.Inject;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.*;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author Daniel
 */
@UseWebDriver
public class TutorialMatcher {

    @Test
    public void simple() {

        String s = "hallo welt";
        
        assertThat("hi", s, anyOf(is(not("hallo welt")), is("hallo welt")));
        verifyThat(1, is(2));
        assertThat("hi", 1, greaterThan(0));
        verifyThat(1, is(1));
        verifyThat(1, is(3));
        assertThat("hi", 1, lessThan(2));        
    }
    
    @Inject
    PlayLogin login;
    
    @Test
    public void testSelenium() {
        navigateTo(login);
        set(login.username, "hallo");
        verifyThat(login.username, attribute("color", is("#123")));
 
        verifyThat(login.username, value(is("allo")));
        
        waitUntil("asda", login.username, value(is("allo")), 60);
        
    }
}
