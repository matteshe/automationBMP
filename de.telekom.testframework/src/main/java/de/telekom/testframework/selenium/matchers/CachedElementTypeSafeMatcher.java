package de.telekom.testframework.selenium.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @author Daniel Biehl
 * @param <E>
 * @param <T>
 */
public abstract class CachedElementTypeSafeMatcher<E, T> extends TypeSafeMatcher<E> {

    protected final Matcher<T> matcher;
    protected boolean hasValue = false;
    protected Object value = null;
    protected final String name;

    public CachedElementTypeSafeMatcher(String name) {
        this(name, null);
    }

    public CachedElementTypeSafeMatcher(String name, Matcher<T> matcher) {
        this.name = name;
        this.matcher = matcher;
    }

    protected Object getCachedValue(E item) {
        if (!hasValue) {
            this.value = getValue(item);
            hasValue = true;
        }

        return this.value;
    }

    protected abstract Object getValue(E item);

    @Override
    public boolean matchesSafely(E item) {
        hasValue = false;

        if (matcher == null) {
            return (boolean) getCachedValue(item);
        }
        return matcher.matches(getCachedValue(item));
    }

    @Override
    protected void describeMismatchSafely(E item, Description mismatchDescription) {
        mismatchDescription.appendText(name);
        if (matcher == null) {
            mismatchDescription.appendText(" was ").appendValue(getCachedValue(item));
        } else {
            mismatchDescription.appendText(" ");
            matcher.describeMismatch(value, mismatchDescription);
        }
    }

    @Override
    public void describeTo(Description description) {

        if (matcher != null) {
            description.appendText(name);

            description.appendText(" ");
            matcher.describeTo(description);
        } else {
            description.appendText(name);
        }
    }

}
