package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel
 */
public class RadioButton extends Control {

    public RadioButton(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    @Override
    protected void internalClick() {
        ensureIsVisible();
        ensureIsEnabled();

        webElement.click();
    }

    @Override
    public Object get() {
        return isSelected();
    }

    @Override
    protected void internalSet(Object value) {
        boolean state;

        if (value instanceof Integer) {
            state = (int) value != 0;
        } else if (value instanceof String) {
            state = Boolean.valueOf((String) value);
        } else {
            state = (boolean) value;
        }

        if (state == true) {
            internalClick();
        }
    }

    public void select() {
        handle("select", new Runnable() {

            @Override
            public void run() {
                internalSet(true);
            }
        });
    }

}
