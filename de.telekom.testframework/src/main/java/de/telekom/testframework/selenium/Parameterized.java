
package de.telekom.testframework.selenium;

/**
 *
 * @author Daniel Biehl
 * @param <T>
 */
public interface Parameterized<T> {
    T get(Object ...params);
}
