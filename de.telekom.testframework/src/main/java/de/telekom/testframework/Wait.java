package de.telekom.testframework;

import com.google.common.base.Function;
import de.telekom.testframework.selenium.WebDriverWrapper;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Matcher;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.FluentWait;

/**
 *
 * @author Daniel
 */
public final class Wait {

    public final static long DEFAULT_SLEEP_TIMEOUT = 500;
    public final static long DEFAULT_TIMEOUT = 30;

    public static <T> void until(final T actual, final Matcher<? super T> matcher, long timeOutInSeconds, long sleepInMillis) {
        Wait.until(null, actual, matcher, timeOutInSeconds, sleepInMillis);
    }

    public static <T> void until(final T actual, final Matcher<? super T> matcher, long timeOutInSeconds, TimeUnit timeOutUnit) {
        until(null, actual, matcher, timeOutInSeconds, timeOutUnit, DEFAULT_SLEEP_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static <T> void until(final T actual, final Matcher<? super T> matcher, long timeOutInSeconds) {
        Wait.until(actual, matcher, timeOutInSeconds, TimeUnit.SECONDS);
    }

    public static <T> void until(final T actual, final Matcher<? super T> matcher) {
        Wait.until(actual, matcher, DEFAULT_TIMEOUT);
    }

    public static <T> void until(String message, final T actual, final Matcher<? super T> matcher, long timeOutInSeconds, TimeUnit timeOutUnit) {
        until(message, actual, matcher, timeOutInSeconds, timeOutUnit, DEFAULT_SLEEP_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static <T> void until(String message, final T actual, final Matcher<? super T> matcher) {
        Wait.until(message, actual, matcher, DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

    public static <T> void until(String message, final T actual, final Matcher<? super T> matcher, long timeOutInSeconds) {
        Wait.until(message, actual, matcher, timeOutInSeconds, TimeUnit.SECONDS);
    }

    public static <T> void until(String message, final T actual, final Matcher<? super T> matcher, long timeOutInSeconds, long sleepInMillis) {
        until(message, actual, matcher, timeOutInSeconds, TimeUnit.SECONDS, sleepInMillis, TimeUnit.MILLISECONDS);
    }

    public static <T> void until(String message, final T actual, final Matcher<? super T> matcher, long timeOut, TimeUnit timeOutUnit, long sleep, TimeUnit sleepUnit) {

        FluentWait<T> waiter = new FluentWait<>(actual);

        waiter.withTimeout(timeOut, timeOutUnit);
        waiter.pollingEvery(sleep, sleepUnit);

        waiter.withMessage(MatcherHelper.buildMatcherMessage("waited until ", actual, matcher, message, true));

        if (actual instanceof WebDriverWrapper) {
            waiter.ignoring(NotFoundException.class);
            waiter.ignoring(StaleElementReferenceException.class);
        }

        waiter.until(new Function<T, Boolean>() {
            @Override
            public Boolean apply(T input) {
                return matcher.matches(input);
            }
        });
    }

    public static <T, V> V until(T input, Function<T, V> isTrue) {
        return until(null, input, isTrue, DEFAULT_TIMEOUT);
    }

    public static <T, V> V until(T input, Function<T, V> isTrue, long timeOutInSeconds) {
        return until(null, input, isTrue, timeOutInSeconds, TimeUnit.SECONDS);
    }

    public static <T, V> V until(T input, Function<T, V> isTrue, long timeOutInSeconds, long sleepInMillies) {
        return until(null, input, isTrue, timeOutInSeconds, TimeUnit.SECONDS, sleepInMillies, TimeUnit.MILLISECONDS);
    }

    public static <T, V> V until(T input, Function<T, V> isTrue, long timeOut, TimeUnit timeOutUnit) {
        return until(null, input, isTrue, timeOut, timeOutUnit, DEFAULT_SLEEP_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static <T, V> V until(String message, T input, Function<T, V> isTrue) {
        return until(message, input, isTrue, DEFAULT_TIMEOUT);
    }

    public static <T, V> V until(String message, T input, Function<T, V> isTrue, long timeOutInSeconds) {
        return until(message, input, isTrue, timeOutInSeconds, TimeUnit.SECONDS);
    }

    public static <T, V> V until(String message, T input, Function<T, V> isTrue, long timeOutInSeconds, long sleepInMillies) {
        return until(message, input, isTrue, timeOutInSeconds, TimeUnit.SECONDS, sleepInMillies, TimeUnit.MILLISECONDS);
    }

    public static <T, V> V until(String message, T input, Function<T, V> isTrue, long timeOut, TimeUnit timeOutUnit) {
        return until(message, input, isTrue, timeOut, timeOutUnit, DEFAULT_SLEEP_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static <T, V> V until(String message, T input, Function<T, V> isTrue, long timeOut, TimeUnit timeOutUnit, long sleepIn, TimeUnit sleepInUnit) {
        FluentWait<T> waiter = new FluentWait<>(input);

        waiter.withTimeout(timeOut, timeOutUnit);
        waiter.pollingEvery(sleepIn, sleepInUnit);

        if (message != null && !message.isEmpty()) {
            waiter.withMessage(message);
        }

        if (input instanceof WebDriverWrapper) {
            waiter.ignoring(NotFoundException.class);
            waiter.ignoring(StaleElementReferenceException.class);
            waiter.ignoring(NoSuchElementException.class);
        }

        return waiter.until(isTrue);
    }
}
