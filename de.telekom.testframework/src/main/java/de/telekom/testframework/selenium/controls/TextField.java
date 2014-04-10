package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextField extends Control {

    public TextField(WebDriver driver, FieldElementLocator locator,WebElement webElement) {
        super(driver, locator, webElement);
    }

    @Override
    public void set(final Object value) {
        if (value == null) {
            return;
        }

        handle("set", new Runnable() {

            @Override
            public void run() {
                ensureIsVisible();
                ensureIsEnabled();
                
            	clear();
                sendKeys(value.toString());
            }
        }, value);
    }
    
    @Override
    public void set(final Keys value) {
        if (value == null) {
            return;
        }

        handle("set Key", new Runnable() {

            @Override
            public void run() {
                ensureIsVisible();
                ensureIsEnabled();
                
                sendKeys((Keys)value);
            }
        }, value);
    }

    @Override
    public Object get() {
        return getValue();
    }

}
