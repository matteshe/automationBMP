package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextField extends Control {

    public TextField(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    @Override
    protected void internalSet(Object value) {
        ensureIsVisible();
        ensureIsEnabled();

        clear();
        sendKeys(value.toString());
    }

    @Override
    protected void internalSendKeys(final CharSequence... keysToSend) {
        ensureIsVisible();
        ensureIsEnabled();

        webElement.sendKeys(keysToSend);
    }
}
