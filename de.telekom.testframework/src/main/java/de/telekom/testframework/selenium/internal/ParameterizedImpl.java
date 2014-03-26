package de.telekom.testframework.selenium.internal;

import de.telekom.testframework.selenium.Parameterized;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Daniel Biehl
 * @param <T>
 */
public class ParameterizedImpl<T> implements Parameterized<T>, ParameterGetter {

    protected final WebDriver webDriver;
    protected final FieldElementLocator pageElementLocator;
    private final T element;

    ParameterizedImpl(WebDriver webDriver, FieldElementLocator pageElementLocator, T element) {
        this.webDriver = webDriver;
        this.pageElementLocator = pageElementLocator;
        this.element = element;
    }

    Object[] parameters;

    @Override
    public T get(Object... params) {
        parameters = params;
        pageElementLocator.setParameterGetter(this);
        return element;
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }
}
