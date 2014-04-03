package de.telekom.testframework;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 *
 * @author Daniel
 */
class MatcherHelper {

    protected static <T> String buildMatcherMessage(String start, T actual, Matcher<? super T> matcher, String message, boolean result) {
        Description expectedDescription = new StringDescription();
        expectedDescription.appendValue(actual);

        expectedDescription.appendText(" ");

        matcher.describeTo(expectedDescription);
        String s = (message != null && !message.isEmpty() ? message + ": " : "") + start + expectedDescription;
        if (!result) {
            Description failedDescription = new StringDescription();

            matcher.describeMismatch(actual, failedDescription);

            s += " failed, because " + failedDescription;
        }
        return s;
    }
}
