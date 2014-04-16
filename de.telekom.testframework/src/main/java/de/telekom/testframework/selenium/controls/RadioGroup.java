package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.WebElementContainer;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel
 */
public class RadioGroup extends Control {

    public RadioGroup(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
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
        return getValue();
    }

    public List<RadioButton> getRadioButtons() {
        String name = getAttribute("name");
        if (name != null) {
            return WebElementContainer.findAll(RadioButton.class,
                    locator.searchContextGetter,
                    webDriver,
                    By.xpath(String.format("//input[@type='radio' and @name='%s']", name)));
        }
        return new ArrayList<>();
    }

    @Override
    protected void internalSet(Object value) {
        List<RadioButton> radioButtons = getRadioButtons();

        if (value instanceof Number) {
            int i = ((Number) value).intValue();

            if (i >= radioButtons.size() || i < 0) {
                throw new NoSuchElementException("cannot find a radio button with index " + i);
            }

            getRadioButtons().get(i).select();

            return;
        }

        String strValue = value.toString();
        for (RadioButton b : radioButtons) {
            if (strValue.equals(b.getValue())) {
                b.select();
                return;
            }
            String id = b.getId();
            if (id != null) {
                List<Text> labels = WebElementContainer.findAll(Text.class,
                        locator.searchContextGetter,
                        webDriver,
                        By.xpath(String.format("//label[@for='%s']", id)));

                if (labels.size() == 1) {
                    if (strValue.equals(labels.get(0).getText())) {
                        b.select();
                        return;
                    }
                }
            }
        }

        throw new NoSuchElementException("cannot find a radio button with value or label" + strValue);
    }

    public void select(final Object value) {
        handle("select", new Runnable() {

            @Override
            public void run() {
                internalSet(value);
            }
        });
    }

}
