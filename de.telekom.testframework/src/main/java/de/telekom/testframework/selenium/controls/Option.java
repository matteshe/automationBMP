package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel
 */
public class Option extends Control {

    public Option(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    @Override
    protected void internalClick() {
        ensureIsEnabled();

        webElement.click();
    }

    public int getIndex() {
        return Integer.parseInt(getAttribute("index"));
    }

    public void select() {
        if (!isSelected()) {
            internalClick();
        }
    }

    public void unselect() {
        if (isSelected()) {
            internalClick();
        }
    }

}
