package de.telekom.testframework.selenium.tests;

import static de.telekom.testframework.Assert.*;
import de.telekom.testframework.annotations.NoVerificationErrors;
import de.telekom.testframework.reporting.Reporter;
import static de.telekom.testframework.selenium.Matchers.url;
import de.telekom.testframework.selenium.matchers.CachedElementTypeSafeMatcher;
import java.io.IOException;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
public class MatcherTest {

    public static Matcher<Integer> one() {
        return new CachedElementTypeSafeMatcher<Integer, Boolean>("bala") {

            @Override
            protected Object getValue(Integer item) {
                return item == 1;
            }
        };
    }

    public static Matcher<Integer> one(final Matcher<Boolean> matcher) {
        return new CachedElementTypeSafeMatcher<Integer, Boolean>("bala", matcher) {

            @Override
            protected Object getValue(Integer item) {
                return item == 1;
            }
        };
    }

    @BeforeTest
    @NoVerificationErrors
    public void beforeTest() {
        verifyThat(1, is(2));
    }

    @BeforeSuite
    @NoVerificationErrors
    public void beforeSuite() {
        verifyThat(1, is(2));
    }

    @BeforeMethod
    @NoVerificationErrors
    public void beforeMethod() {
        verifyThat(1, is(2));
    }

    @Test
    public void test1() {
        verifyThat(1, is(one()));
    }

    @Test
    @NoVerificationErrors
    public void test2() {
        verifyThat(2, is(one()));
    }

    @Test
    @NoVerificationErrors
    public void test3() {
        verifyThat(2, one(is(true)));
    }

    @Test
    public void test4() {
        verifyThat(1, one(is(true)));
    }

    @Test
    public void test5() {
        verifyThat("abc", containsString("a"));
        verifyThat(1, one(is(true)));
        verifyThat(1, isOneOf(1, 2, 3));
        verifyThat(1, is(not(isOneOf(5, 2, 3))));
    }

    @SafeVarargs
    public static Matcher<Runnable> throwing(final Class<? extends Throwable> throwableClass, final Class<? extends Throwable>... others) {

        String othernames = "";
        for (Class<?> c : others) {
            if (!othernames.isEmpty()) {
                othernames += " or ";
            }
            othernames += c.getSimpleName();
        }

        return new CachedElementTypeSafeMatcher<Runnable, Boolean>("throws " + throwableClass.getSimpleName() + (othernames.isEmpty() ? "" : " or " + othernames)) {

            @Override
            protected Object getValue(Runnable item) {
                try {
                    item.run();
                } catch (Throwable t) {
                    if (throwableClass.isInstance(t)) {
                        return true;
                    }

                    for (Class<?> c : others) {
                        if (c.isInstance(t)) {
                            return true;
                        }
                    }

                    throw t;
                }
                return false;
            }
        };
    }

    @Test
    public void test6() {
        verifyThat(new Runnable() {

            @Override
            public void run() {
                Reporter.reportMessage("I am running...");
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }, is(throwing(IOException.class, UnsupportedOperationException.class)));
    }

    @Test
    public void test7() {
        verifyThat("http://www.heise.de", is(url()));
    }
}
