package de.telekom.testframework;

import de.telekom.testframework.selenium.Application;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Control;
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
}
