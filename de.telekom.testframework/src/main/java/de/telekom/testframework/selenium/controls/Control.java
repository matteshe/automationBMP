package de.telekom.testframework.selenium.controls;

import com.google.common.base.Function;

import de.telekom.testframework.Wait;
import de.telekom.testframework.selenium.ElementNotEnabledException;
import de.telekom.testframework.selenium.internal.FieldElementLocator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Daniel
 */
public class Control extends DelegatedWebElement {

    public Control(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    public String getValue() {
        return getAttribute("value");
    }

    public void set(Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void set(Keys value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object get() {
        return getValue();
    }

    public void scrollIntoView() {
        ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public void ensureIsEnabled() {
        boolean b = false;
        Throwable cause = null;
        try {
            b = Wait.until(Control.this, new Function<Control, Boolean>() {
                @Override
                public Boolean apply(Control input) {
                    return input.isEnabled();
                }
            });
        } catch (TimeoutException e) {
            cause = e.getCause();
        }
        if (!b) {
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new ElementNotEnabledException(String.format("element is not enabled", this), cause);
        }
    }

    public void ensureIsVisible() {
        boolean b = false;
        Throwable cause = null;
        try {
            b = Wait.until(Control.this, new Function<Control, Boolean>() {
                @Override
                public Boolean apply(Control input) {
                    return input.isDisplayed();
                }
            });
        } catch (TimeoutException e) {
            cause = e.getCause();
        }
        if (!b) {
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }

            throw new ElementNotVisibleException(String.format("element is not visible", this), cause);
        }
    }

    @Override
    public void click() {
        handle("click", new Runnable() {

            @Override
            public void run() {
                internalClick();
            }
        });
    }

    protected void internalClick() {
        ensureIsVisible();

        webElement.click();
    }

    public void moveTo() {
        handle("moveTo", new Runnable() {

            @Override
            public void run() {
                ensureIsVisible();

                Actions actions = new Actions(webDriver);
                actions.moveToElement(webElement).perform();
            }
        });
    }

    public void moveTo(final int x, final int y) {
        handle("moveTo", new Runnable() {

            @Override
            public void run() {
                ensureIsVisible();

                Actions actions = new Actions(webDriver);
                actions.moveToElement(webElement, x, y).perform();
            }
        }, x, y);
    }
}
