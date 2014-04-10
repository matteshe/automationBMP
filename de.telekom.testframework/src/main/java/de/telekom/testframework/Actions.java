package de.telekom.testframework;

import de.telekom.testframework.selenium.Application;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Control;
import de.telekom.testframework.selenium.controls.DelegatedWebElement;

import java.net.URL;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
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

    public static void navigateTo(Browser browser, String url) {
        browser.navigate().to(url);
    }

    public static void navigateTo(Browser browser, URL url) {
        browser.navigate().to(url);
    }

    public static void set(Control control, Object value) {
        control.set(value);
    }
    
    public static void set(Control control, Keys value) {
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
                String result = null;
                try {
                    result = browser.getCurrentWindowHandle();
                } catch (org.openqa.selenium.NoSuchWindowException ex) {

                }
                browser.switchTo().window(nameOrHandle);
                return result;
            }
        };
    }

    public static SwitchToAction<Boolean> frame(final String nameOrId) {
        return new SwitchToAction<Boolean>() {

            @Override
            public Boolean apply(Browser browser) {
                browser.switchTo().frame(nameOrId);
                return true;
            }
        };
    }

    public static SwitchToAction<Boolean> frame(final int index) {
        return new SwitchToAction<Boolean>() {

            @Override
            public Boolean apply(Browser browser) {
                browser.switchTo().frame(index);
                return true;
            }
        };
    }

    public static SwitchToAction<Boolean> frame(final WebElement frameElement) {
        return new SwitchToAction<Boolean>() {

            @Override
            public Boolean apply(Browser browser) {
                browser.switchTo().frame(frameElement);
                return true;
            }
        };
    }

    public static SwitchToAction<WebDriver> defaultContent() {
        return new SwitchToAction<WebDriver>() {

            @Override
            public WebDriver apply(Browser browser) {
                return browser.switchTo().defaultContent();
            }
        };
    }

    public static SwitchToAction<WebElement> activeElement() {
        return new SwitchToAction<WebElement>() {

            @Override
            public WebElement apply(Browser browser) {
                return browser.switchTo().activeElement();
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

    public static <T> T switchTo(SwitchToAction<T> action) {
        return switchTo(Browser.getInstance(), action);
    }

    public static String newWindow() {
        return Browser.getInstance().newWindow();
    }

    public static String closeWindow() {
        return Browser.getInstance().closeWindow();
    }

    public static String getCurrentWindowHandle() {
        return Browser.getInstance().getCurrentWindowHandle();
    }

    public static String newTab() {
        return Browser.getInstance().newTab();
    }

    public static void navigateTo(String url) {
        Browser.getInstance().navigate().to(url);
    }

    public static void navigateTo(URL url) {
        Browser.getInstance().navigate().to(url);
    }

    public static WebElement find(By by) {
        return Browser.getInstance().find(by);
    }

    public static <T extends DelegatedWebElement> T find(Class<T> clz, By by) {
        return Browser.getInstance().find(clz, by);
    }
}
