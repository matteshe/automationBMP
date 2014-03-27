package de.telekom.testframework.tests;

import static de.telekom.testframework.Assert.*;
import static java.util.concurrent.TimeUnit.*;

import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
public class WaitTest {

    @Test
    public void testWaitFor0Seconds() {
        waitFor(0, SECONDS);
    }

    @Test
    public void testWaitFor1Seconds() {
        waitFor(1, SECONDS);
    }

    @Test
    public void testWaitFor20Seconds() {
        waitFor(20, SECONDS);
    }

    @Test
    public void testWaitFor500Milliseconds() {
        waitFor(500, MILLISECONDS);
    }
}
