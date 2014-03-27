package de.telekom.testframework.selenium.tests;

import com.google.inject.Inject;

import static de.telekom.testframework.Actions.*;

import static de.telekom.testframework.selenium.Matchers.*;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.controls.Select;
import de.telekom.testframework.selenium.tests.testapplication.TestApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@UseWebDriver
public class ApplicationTest {

    @Inject
    TestApplication app;

    @Test
    public void findADiv() {
        app.navigateTo();

        assertThat(app.find(By.tagName("div")), exists());
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void throwsExceptionIfCannotFindControl() {
        app.navigateTo();

        click(app.find(By.tagName("adf")));
    }
    
    @Test
    public void findGeneric() {
        app.navigateTo();

        Select a = app.find(Select.class, By.id("aSelect"));
        
        a.select(0,1,2);
    }
}
