package de.telekom.bmp.tests.playground;

import static de.telekom.testframework.Assert.*;
import de.telekom.testframework.annotations.QCId;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.is;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@QCId("5564")
@UseWebDriver
public class NewEmptyTestNGTest1 {

//  @Inject
//  Login login;
//  
//  @Inject
//  Home home;
  
  @Test
  public void theTest() {
      //login.navigateTo();
      
      //assertThat(home, isCurrentPage());
  }
  
  @Test
  public void theTest1() {
      assertThat(1, is(2));
  }
  
  @Test
  public void theTest2() {
      
  }
  
  
}
