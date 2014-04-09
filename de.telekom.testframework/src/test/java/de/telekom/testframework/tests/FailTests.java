
package de.telekom.testframework.tests;

import static de.telekom.testframework.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
public class FailTests {
    
    @Test(expectedExceptions = AssertionError.class)
    public void failWithoutMessage() {
        fail();
    }
    
    @Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = "a message")
    public void failWithMessage() {
        fail("a message");
    }
        
}
