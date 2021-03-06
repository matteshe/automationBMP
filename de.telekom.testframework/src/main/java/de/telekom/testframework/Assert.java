package de.telekom.testframework;

import com.google.common.base.Function;
import de.telekom.testframework.reporting.Reporter;
import de.telekom.testframework.selenium.ActionHelper;
import de.telekom.testframework.selenium.WebDriverWrapper;
import java.util.concurrent.TimeUnit;
import org.hamcrest.Matcher;
import org.openqa.selenium.OutputType;
import static de.telekom.testframework.Wait.DEFAULT_SLEEP_TIMEOUT;
import static de.telekom.testframework.Wait.DEFAULT_TIMEOUT;

/**
 *
 * @author Daniel Biehl
 */
public class Assert {

    public static void waitFor(long time, TimeUnit timeUnit) {
        waitFor(time, timeUnit, null);
    }

    public static void waitFor(long time, TimeUnit timeUnit, String cause) {

        if (cause != null) {
            Reporter.entering(null, "wait for", time, timeUnit, cause);
        } else {
            Reporter.entering(null, "wait for", time, timeUnit);
        }

        try {

            Wait.until(false, new Function<Boolean, Boolean>() {
                @Override
                public Boolean apply(Boolean input) {
                    return false;
                }
            }, time, timeUnit);
        } catch (Throwable ex) {

        } finally {
            Reporter.exiting(null, "wait for");
        }
    }

    public static <T> void waitUntil(final T actual, final Matcher<? super T> matcher, long timeOutInSeconds, long sleepInMillis) {
        waitUntil(null, actual, matcher, timeOutInSeconds, sleepInMillis);
    }

    public static <T> void waitUntil(final T actual, final Matcher<? super T> matcher, long timeOut, TimeUnit timeOutUnit) {
        waitUntil(null, actual, matcher, timeOut, timeOutUnit, DEFAULT_SLEEP_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static <T> void waitUntil(final T actual, final Matcher<? super T> matcher, long timeOutInSeconds) {
        waitUntil(actual, matcher, timeOutInSeconds, TimeUnit.SECONDS);
    }

    public static <T> void waitUntil(final T actual, final Matcher<? super T> matcher) {
        waitUntil(actual, matcher, DEFAULT_TIMEOUT);
    }

    public static <T> void waitUntil(String message, final T actual, final Matcher<? super T> matcher, long timeOut, TimeUnit timeOutUnit) {
        waitUntil(message, actual, matcher, timeOut, timeOutUnit, DEFAULT_SLEEP_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static <T> void waitUntil(String message, final T actual, final Matcher<? super T> matcher) {
        waitUntil(message, actual, matcher, DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

    public static <T> void waitUntil(String message, final T actual, final Matcher<? super T> matcher, long timeOutInSeconds) {
        waitUntil(message, actual, matcher, timeOutInSeconds, TimeUnit.SECONDS);
    }

    public static <T> void waitUntil(String message, final T actual, final Matcher<? super T> matcher, long timeOutInSeconds, long sleepInMillis) {
        waitUntil(message, actual, matcher, timeOutInSeconds, TimeUnit.SECONDS, sleepInMillis, TimeUnit.MILLISECONDS);
    }

    public static <T> void waitUntil(String message, final T actual, final Matcher<? super T> matcher, long timeOut, TimeUnit timeOutUnit, long sleep, TimeUnit sleepUnit) {

        if (message != null) {
            Reporter.entering(null, "wait until", message, actual, matcher, timeOut, timeOutUnit, sleep, sleepUnit);
        } else {
            Reporter.entering(null, "wait until", actual, matcher, timeOut, timeOutUnit, sleep, sleepUnit);
        }

        try {
            Wait.until(message, actual, matcher, timeOut, timeOutUnit, sleep, sleepUnit);
        } catch (Throwable ex) {
            Reporter.reportException(ex);

            if (actual instanceof WebDriverWrapper) {
                Reporter.reportScreenshot(ActionHelper.getScreenshotAs((WebDriverWrapper) actual, OutputType.BYTES));
            }

            throw ex;
        } finally {
            Reporter.exiting(null, "wait until");
        }
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher, DEFAULT_TIMEOUT, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher, long timeOutInSeconds) {
        assertThat("", actual, matcher, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> void assertThat(String message, T actual, Matcher<? super T> matcher) {
        assertThat(message, actual, matcher, DEFAULT_TIMEOUT, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> void assertThat(String message, T actual, Matcher<? super T> matcher, long timeOutInSeconds) {
        assertThat(message, actual, matcher, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> void assertThat(String message, T actual, Matcher<? super T> matcher, long timeOutInSeconds, long sleepInMillis) {
        Reporter.entering(null, "assert that", message, actual, matcher);

        try {
            boolean result = MatcherHelper.match(actual, matcher, timeOutInSeconds, sleepInMillis);

            String s = MatcherHelper.buildMatcherMessage("Assert that", actual, matcher, message, result);

            AssertionError ex = null;
            if (!result) {
                ex = new AssertionError(s);
            }

            Reporter.reportAssertion(s, result, ex);

            if (ex != null) {
                Reporter.reportException(ex);

                if (actual instanceof WebDriverWrapper) {
                    Reporter.reportScreenshot(ActionHelper.getScreenshotAs((WebDriverWrapper) actual, OutputType.BYTES));
                }

                throw ex;
            }
        } finally {
            Reporter.exiting(null, "assert that");
        }
    }

    public static void assertThat(boolean expression) {
        assertThat(null, expression);
    }

    public static void assertThat(String message, boolean expression) {

        if (message == null) {
            Reporter.entering(null, "assert that", expression);
        } else {
            Reporter.entering(null, "assert that", message, expression);
        }
        try {
            AssertionError ex = null;
            if (!expression) {
                if (message == null) {
                    ex = new AssertionError();
                } else {
                    ex = new AssertionError("Assert that " + message);
                }
            }

            Reporter.reportAssertion(message, expression, ex);

            if (ex != null) {
                Reporter.reportException(ex);
                throw ex;
            }
        } finally {
            Reporter.exiting(null, "assert that");
        }

    }

    public static <T> void verifyThat(T actual, Matcher<? super T> matcher) {
        verifyThat("", actual, matcher, DEFAULT_TIMEOUT, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> void verifyThat(T actual, Matcher<? super T> matcher, long timeOutInSeconds) {
        verifyThat("", actual, matcher, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> void verifyThat(String message, T actual, Matcher<? super T> matcher) {
        verifyThat(message, actual, matcher, DEFAULT_TIMEOUT, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> void verifyThat(String message, T actual, Matcher<? super T> matcher, long timeOutInSeconds) {
        verifyThat(message, actual, matcher, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> void verifyThat(String message, T actual, Matcher<? super T> matcher, long timeOutInSeconds, long sleepInMillis) {
        Reporter.entering(null, "verify that", message, actual, matcher);
        try {
            boolean result = MatcherHelper.match(actual, matcher, timeOutInSeconds, sleepInMillis);

            String s = MatcherHelper.buildMatcherMessage("Verify that", actual, matcher, message, result);

            Throwable ex = null;
            if (!result) {
                ex = new VerificationError(s);
            }
            Reporter.reportVerification(s, result, ex);

            if (ex != null) {
                Reporter.reportException(ex);

                if (actual instanceof WebDriverWrapper) {
                    Reporter.reportScreenshot(ActionHelper.getScreenshotAs((WebDriverWrapper) actual, OutputType.BYTES));
                }
            }

        } finally {
            Reporter.exiting(null, "verify that");
        }
    }

    public static void verifyThat(boolean expression) {
        verifyThat(null, expression);
    }

    public static void verifyThat(String message, boolean expression) {
        if (message == null) {
            Reporter.entering(null, "verify that", expression);
        } else {
            Reporter.entering(null, "verify that", message, expression);
        }
        try {
            VerificationError ex = expression ? null : message != null ? new VerificationError(message) : new VerificationError();

            Reporter.reportVerification(message, expression, ex);

            if (ex != null) {
                Reporter.reportException(ex);
            }
        } finally {
            Reporter.exiting(null, "verify that");
        }
    }

    public static void fail() {
        throw new AssertionError();
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    public final static long DEFAULT_CHECK_TIMEOUT = 5;
    
    public static <T> boolean checkThat(T actual, Matcher<? super T> matcher) {
        return checkThat("", actual, matcher, DEFAULT_CHECK_TIMEOUT, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> boolean checkThat(T actual, Matcher<? super T> matcher, long timeOutInSeconds) {
        return checkThat("", actual, matcher, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> boolean checkThat(String message, T actual, Matcher<? super T> matcher) {
        return checkThat(message, actual, matcher, DEFAULT_CHECK_TIMEOUT, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> boolean checkThat(String message, T actual, Matcher<? super T> matcher, long timeOutInSeconds) {
        return checkThat(message, actual, matcher, timeOutInSeconds, DEFAULT_SLEEP_TIMEOUT);
    }

    public static <T> boolean checkThat(String message, T actual, Matcher<? super T> matcher, long timeOutInSeconds, long sleepInMillis) {
        Reporter.entering(null, "check that", message, actual, matcher);
        try {
            boolean result = MatcherHelper.match(actual, matcher, timeOutInSeconds, sleepInMillis);

            String s = MatcherHelper.buildMatcherMessage("Check that", actual, matcher, message, result);

            Reporter.reportCheck(s, result);

            if (!result) {
                if (actual instanceof WebDriverWrapper) {
                    Reporter.reportScreenshot(ActionHelper.getScreenshotAs((WebDriverWrapper) actual, OutputType.BYTES));
                }
            }

            return result;
        } finally {
            Reporter.exiting(null, "check that");
        }
    }

}
