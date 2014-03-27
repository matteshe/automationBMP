package de.telekom.testframework.selenium.tests;

import com.google.inject.Inject;

import static de.telekom.testframework.Actions.*;

import static de.telekom.testframework.selenium.Matchers.*;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.tests.testapplication.TestApplication;
import org.openqa.selenium.By;
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
    public void hello() {
        app.navigateTo();

        assertThat(app.find(Text.class, By.tagName("div")), exists());
    }
}
