package de.telekom.testframework.selenium.tests;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.ResetWebDriver;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.controls.FileInput;
import de.telekom.testframework.selenium.tests.testapplication.TestApplication;
import java.io.File;
import javax.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@UseWebDriver
public class FileInputTest {

    @Inject
    Browser browser;

    @Inject
    TestApplication app;

    @Inject
    WebDriver driver;

    @Test
    @ResetWebDriver
    public void setFileInput() {
        navigateTo(app);

        browser.find(FileInput.class, By.name("theFileInput")).set(new File(this.getClass().getResource("newfile.txt").toExternalForm()));
    }

}
