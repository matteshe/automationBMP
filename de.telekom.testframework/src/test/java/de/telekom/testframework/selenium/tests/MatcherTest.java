package de.telekom.testframework.selenium.tests;

import static de.telekom.testframework.Assert.*;
import de.telekom.testframework.annotations.NoVerificationErrors;
import de.telekom.testframework.selenium.matchers.CachedElementTypeSafeMatcher;
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
}
