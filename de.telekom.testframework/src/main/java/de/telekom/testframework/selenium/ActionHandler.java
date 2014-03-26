package de.telekom.testframework.selenium;

import com.google.common.base.Function;
import de.telekom.testframework.Wait;
import de.telekom.testframework.reporting.Reporter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.UnhandledAlertException;

/**
 *
 * @author Daniel Biehl
 */
public class ActionHandler {

    public static <X> X getScreenshotAs(WebDriverWrapper wrapper, OutputType<X> target) throws WebDriverException {

        WebDriver webDriver = wrapper.getWebDriver();
        if (webDriver == null) {
            return null;
        }

        TakesScreenshot takesScreenshot = null;

        if (webDriver instanceof TakesScreenshot) {
            takesScreenshot = (TakesScreenshot) webDriver;
        } else {
            WebDriver augmentedDriver = new Augmenter().augment(webDriver);
            if (augmentedDriver instanceof TakesScreenshot) {
                takesScreenshot = (TakesScreenshot) augmentedDriver;
            }
        }
        if (takesScreenshot == null) {
            return null;
        }
        return takesScreenshot.getScreenshotAs(target);
    }

    static Object lastContext = null;

    public static void needWaitForPageLoad(boolean state) {
        isInWaitForPageLoaded = !state;
    }

    static boolean isInWaitForPageLoaded = false;

    public static void waitUntilPageLoaded(WebDriver driver, Object context) {
        if (isInWaitForPageLoaded) {
            return;
        }

        if (context == lastContext) {
            return;
        }

        lastContext = context;

        if (context == null) {
            return;
        }

        //Reporter.entering(null, "waitUntilPageLoaded", context);
        isInWaitForPageLoaded = true;
        try {
            Wait.until("Page loaded", driver, new Function<WebDriver, Boolean>() {

                @Override
                public Boolean apply(WebDriver input) {
                    return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
                }
            });
        } finally {
            isInWaitForPageLoaded = false;
            //Reporter.exiting(null, "waitUntilPageLoaded");
        }

    }

    public static void handle(WebDriverWrapper wrapper, String contextName, String actionName, Runnable r, Object... args) {
        Reporter.entering(contextName, actionName, args);

        try {
            //Reporter.reportScreenshot(getScreenshotAs(wrapper, OutputType.BYTES));
            r.run();

        } catch (Throwable e) {
            Reporter.reportException(e);
            Reporter.reportScreenshot(getScreenshotAs(wrapper, OutputType.BYTES));

            throw e;
        } finally {
            //Reporter.reportScreenshot(getScreenshotAs(wrapper, OutputType.BYTES));

            Reporter.exiting(contextName, actionName);
        }
    }
}
