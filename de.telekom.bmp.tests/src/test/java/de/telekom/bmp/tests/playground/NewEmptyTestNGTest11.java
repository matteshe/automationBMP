package de.telekom.bmp.tests.playground;

import static de.telekom.testframework.Assert.*;
import de.telekom.testframework.annotations.QCId;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@QCId("1236")
//@WebDriverTest
public class NewEmptyTestNGTest11 {

    @Test
    public void theTest() {
        assertThat(1, is(1));
    }

    @Test
    public void theTest1() {
        assertThat(1, is(2));
    }
    
     @Test
    public void theTest2() {
        assertThat(1, is(1));
    }
}
