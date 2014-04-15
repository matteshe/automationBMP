package de.telekom.testframework.selenium.tutorial.tests;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.selenium.ElementNotEnabledException;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static de.telekom.testframework.selenium.controls.CheckBox.*;
import de.telekom.testframework.selenium.tutorial.application.TutorialApplication;
import de.telekom.testframework.selenium.tutorial.application.pages.Home;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@UseWebDriver
public class SetVerifyExamples {

    @Inject
    TutorialApplication application;

    @Inject
    Home home;

    @BeforeMethod
    public void before() {
        navigateTo(home);
    }

    @Test
    public void setTextFields() {
        set(home.username, "abc@def.de");
        verifyThat(home.username, value(is("abc@def.de")));
        set(home.password, "apassword");
        verifyThat(home.password, value(is("apassword")));
    }

    @Test(expectedExceptions = ElementNotEnabledException.class)
    public void setDisabledTextFields() {
        set(home.disabledText, "abc@def.de");
    }

    @Test(expectedExceptions = ElementNotVisibleException.class)
    public void setHiddenTextFields() {
        set(home.hiddenText, "abc@def.de");
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void setNotExistingTextFields() {
        set(home.notExistingText, "abc@def.de");
    }
    
    @Test
    public void setTextFieldDelayed() {
        click(home.delayedToggleDisableField);
        assertThat(home.delayedDisabledText, is(not(enabled())));
        set(home.delayedDisabledText, "abc@def.de");        
    }

    @Test
    public void setCheckBoxes() {
        set(home.stayLoggedIn, true);
        verifyThat(home.stayLoggedIn, is(selected()));
        verifyThat(home.stayLoggedIn, is(checked()));
        verifyThat(home.stayLoggedIn, checked(is(true)));
        verifyThat(home.stayLoggedIn, value(is(true)));

        set(home.stayLoggedIn, false);
        verifyThat(home.stayLoggedIn, is(not(checked())));
        verifyThat(home.stayLoggedIn, checked(is(false)));
        verifyThat(home.stayLoggedIn, value(is(false)));

        set(home.stayLoggedIn, 1);
        verifyThat(home.stayLoggedIn, checked(is(true)));
        verifyThat(home.stayLoggedIn, value(is(true)));

        set(home.stayLoggedIn, 0);
        verifyThat(home.stayLoggedIn, checked(is(false)));
        verifyThat(home.stayLoggedIn, value(is(false)));

        set(home.stayLoggedIn, TOGGLE);
        verifyThat(home.stayLoggedIn, checked(is(true)));
        verifyThat(home.stayLoggedIn, value(is(true)));

        set(home.stayLoggedIn, CHECKED);
        verifyThat(home.stayLoggedIn, is(checked()));
        verifyThat(home.stayLoggedIn, checked(is(true)));
        verifyThat(home.stayLoggedIn, value(is(true)));

        set(home.stayLoggedIn, UNCHECKED);
        verifyThat(home.stayLoggedIn, is(not(checked())));
        verifyThat(home.stayLoggedIn, checked(is(false)));
        verifyThat(home.stayLoggedIn, value(is(false)));
    }

    @Test
    public void setRadioButton() {
        set(home.aradioAbc, true);
        verifyThat(home.aradioAbc, is(selected()));
        verifyThat(home.aradioDef, is(not(selected())));
        verifyThat(home.aradioGhi, is(not(selected())));

        set(home.aradioDef, true);
        verifyThat(home.aradioAbc, is(not(selected())));
        verifyThat(home.aradioDef, is(selected()));
        verifyThat(home.aradioGhi, is(not(selected())));

        set(home.aradioGhi, true);
        verifyThat(home.aradioAbc, is(not(selected())));
        verifyThat(home.aradioDef, is(not(selected())));
        verifyThat(home.aradioGhi, is(selected()));
    }

}
