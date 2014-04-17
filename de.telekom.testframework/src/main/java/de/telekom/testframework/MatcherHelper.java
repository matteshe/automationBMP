package de.telekom.testframework;

import com.google.common.base.Function;
import static de.telekom.testframework.Wait.DEFAULT_SLEEP_TIMEOUT;
import static de.telekom.testframework.Wait.DEFAULT_TIMEOUT;
import de.telekom.testframework.selenium.WebDriverWrapper;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.FluentWait;

/**
 *
 * @author Daniel
 */
class MatcherHelper {

    protected static <T> String buildMatcherMessage(String start, T actual, Matcher<? super T> matcher, String message, boolean result) {
        // TODO better matcher messages
        
        Description expectedDescription = new StringDescription();
        expectedDescription.appendValue(actual);

        expectedDescription.appendText(" ");

        matcher.describeTo(expectedDescription);

        String s = start + " " + (message != null && !message.isEmpty() ? message + " > " : "") + expectedDescription;

        if (!result) {
            Description failedDescription = new StringDescription();

            matcher.describeMismatch(actual, failedDescription);

            s += " failed, because " + failedDescription;
        }
        return s;
    }

    protected static <T> boolean match(final T actual, final Matcher<? super T> matcher) {
        return match(actual, matcher, DEFAULT_TIMEOUT, DEFAULT_SLEEP_TIMEOUT);
    }

    protected static <T> boolean match(final T actual, final Matcher<? super T> matcher, long timeOutInSeconds) {
        return match(actual, matcher, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    protected static <T> boolean match(T actual, final Matcher<? super T> matcher, long timeOutInSeconds, long sleepInMillis) {

        if (timeOutInSeconds > 0) {
            if (actual instanceof WebDriverWrapper) {
                try {
                    FluentWait<T> waiter = new FluentWait<>(actual);

                    boolean result = waiter
                            .withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
                            .pollingEvery(sleepInMillis, TimeUnit.MILLISECONDS)
                            .ignoring(NotFoundException.class)
                            .ignoring(StaleElementReferenceException.class)
                            .until(new Function<T, Boolean>() {

                                @Override
                                public Boolean apply(T input) {
                                    return matcher.matches(input);
                                }
                            });

                    return result;

                } catch (TimeoutException e) {
                    Throwable cause = e.getCause();
                    if (cause != null && cause instanceof WebDriverException) {
                        throw (WebDriverException) cause;
                    }
                }
                return false;
            }
        }

        return matcher.matches(actual);
    }

}
