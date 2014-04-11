package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel
 */
public class CheckBox extends Control {

    private static class Toggle {
    }

    public static final Object TOGGLE = new Toggle();
    public static final Object CHECKED = Boolean.TRUE;
    public static final Object UNCHECKED = Boolean.FALSE;

    public CheckBox(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    @Override
    protected void internalClick() {
        ensureIsVisible();
        ensureIsEnabled();

        webElement.click();
    }

    public boolean isChecked() {
        return isSelected();
    }

    @Override
    public Object get() {
        return isChecked();
    }

    @Override
    protected void internalSet(Object value) {
        boolean state;

        if (value instanceof Toggle) {
            state = !isChecked();
        } else if (value instanceof Integer) {
            state = (int) value != 0;
        } else if (value instanceof String) {
            state = Boolean.valueOf((String) value);
        } else {
            state = (boolean) value;
        }

        if (state != isChecked()) {
            internalClick();
        }
    }

    public void check() {
        handle("check", new Runnable() {

            @Override
            public void run() {
                internalSet(CHECKED);
            }
        });
    }

    public void uncheck() {
        handle("uncheck", new Runnable() {

            @Override
            public void run() {
                internalSet(UNCHECKED);
            }
        });
    }

    public void toggle() {
        handle("check", new Runnable() {

            @Override
            public void run() {
                internalSet(TOGGLE);
            }
        });
    }

}
