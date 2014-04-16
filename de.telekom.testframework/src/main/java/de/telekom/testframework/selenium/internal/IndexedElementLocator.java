package de.telekom.testframework.selenium.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel
 */
public class IndexedElementLocator extends FieldElementLocator {

    private final FieldElementLocator parentLocator;
    private final int index;

    public IndexedElementLocator(FieldElementLocator parentLocator, int index) {
        super(parentLocator.searchContextGetter, parentLocator.field, parentLocator.getBy());

        Objects.requireNonNull(parentLocator, "parentLocator must be non null");

        this.parentLocator = parentLocator;
        this.index = index;
    }

    @Override
    public WebElement findElement() {
        List<WebElement> parentElements = parentLocator.findElements();
        if (parentElements.size() > index) {
            return parentElements.get(index);
        }
        throw new NotFoundException("cannot find an element at index " + index + " for locator " + parentLocator);
    }

    @Override
    public List<WebElement> findElements() {
        List<WebElement> result = new ArrayList<>();
        List<WebElement> parentElements = parentLocator.findElements();
        if (parentElements.size() > index) {
            result.add(parentElements.get(index));
        }
        return result;
    }

    @Override
    public String toString() {
        return parentLocator.toString() + "[" + index + "]";
    }
}
