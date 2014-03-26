package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel Biehl
 */
public class Button extends Control {

    public Button(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    @Override
    protected void internalClick() {
        ensureIsVisible();
        ensureIsEnabled();

        webElement.click();
    }

}
