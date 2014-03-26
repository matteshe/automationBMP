package tutorial;

import de.telekom.bmp.pages.playground.FunctionalActions;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.annotations.ResetWebDriver;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.*;

/**
 *
 * @author Daniel
 */
@UseWebDriver
@QCId("5572")
public class Tutorial2 {
    
    @Inject
    FunctionalActions functional;
        
    @ResetWebDriver
    @BeforeMethod
    public void setUpMethod() throws Exception {
        functional.login("tester.bmp@gmail.com", "tester123");
    }

    
    @AfterMethod
    public void tearDownMethod() throws Exception {
        functional.logout();
    }
    
    @Test
    public void theTest1() {
        System.out.println("the test");
    }

//    @Test
//    public void theTest2() {
//        System.out.println("the test");
//    }

}
