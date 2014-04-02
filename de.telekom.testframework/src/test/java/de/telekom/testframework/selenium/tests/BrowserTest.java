package de.telekom.testframework.selenium.tests;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.selenium.Browser;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.ResetWebDriver;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.controls.Select;
import de.telekom.testframework.selenium.tests.testapplication.TestApplication;
import static java.util.concurrent.TimeUnit.SECONDS;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@UseWebDriver
public class BrowserTest {

    @Inject
    Browser browser;

    @Inject
    TestApplication app;

    @Inject
    WebDriver driver;

    void printWindowHandle() {
        for (String s : driver.getWindowHandles()) {
            System.out.println(s);
        }
    }

    @Test
    @ResetWebDriver
    public void newTab() {
        navigateTo(app);
        printWindowHandle();

        click(app.find(By.id("annewwindow")));

        printWindowHandle();

        String lastWindow = browser.newTab();
        printWindowHandle();

        String tabWindow = driver.getWindowHandle();

        navigateTo(app);

        printWindowHandle();

        switchTo(browser, window(lastWindow));

        app.find(Select.class, By.id("aSelect")).select(0, 1, 2);

        switchTo(browser, window(tabWindow));
    }

    @Test
    @ResetWebDriver
    public void navigateTest() {
        navigateTo(app);

        browser.navigate().back();

        navigateTo(app);

        browser.navigate().to("http://www.heise.de");
        verifyThat(browser, currentUrl(containsString("heise")));

        browser.navigate().back();
        verifyThat(browser, currentUrl(containsString("localhost")));

    }
}
