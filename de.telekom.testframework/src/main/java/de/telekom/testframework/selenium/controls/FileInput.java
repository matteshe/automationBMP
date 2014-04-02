package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FileInput extends Control {

    public FileInput(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
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

                if (value instanceof File) {
                    sendKeys(((File) value).getAbsolutePath());
                } else {
                    sendKeys(value.toString());
                }
            }
        }, value);
    }

    @Override
    public Object get() {
        return getValue();
    }

}
