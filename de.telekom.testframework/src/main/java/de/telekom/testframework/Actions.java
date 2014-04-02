package de.telekom.testframework;

import de.telekom.testframework.selenium.Application;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Control;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel Biehl
 */
public class Actions extends Assert {

    public static void navigateTo(Application application, String page) {
        application.navigateTo(page);
    }

    public static void navigateTo(Application application) {
        application.navigateTo();
    }

    public static void navigateTo(Page page) {
        page.navigateTo();
    }

    public static void navigateTo(Page page, String path) {
        page.navigateTo(path);
    }

    public static void set(Control control, Object value) {
        control.set(value);
    }

    public static Object get(Control control, Object value) {
        return control.get();
    }

    public static void click(WebElement element) {
        element.click();
    }

    public static void moveTo(Control control) {
        control.moveTo();
    }

    public static void moveTo(Control control, int x, int y) {
        control.moveTo(x, y);
    }

    public interface SwitchToAction<T> {

        T apply(Browser browser);
    }

    public static SwitchToAction<String> window(final String nameOrHandle) {
        return new SwitchToAction<String>() {

            @Override
            public String apply(Browser browser) {
                String result = browser.getCurrentWindowHandle();
                browser.switchTo().window(nameOrHandle);
                return result;
            }
        };
    }
    
    public static SwitchToAction<Alert> alert() {
        return new SwitchToAction<Alert>() {

            @Override
            public Alert apply(Browser browser) {
                return browser.switchTo().alert();
            }
        };
    }

    public static <T> T switchTo(Browser browser, SwitchToAction<T> action) {
        return action.apply(browser);
    }
    
    
}
