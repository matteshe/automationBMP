package de.telekom.bmp.tests.tutorial;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.playground.PlayHeader;
import de.telekom.bmp.pages.playground.PlayLogin;
import de.telekom.bmp.pages.playground.PlayPortal;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */

@UseWebDriver
public class Tutorial1 {
    
    @Inject
    FunctionalActions functional;
    
    @Inject
    PlayHeader header;
    
    @Inject
    PlayLogin login;
    
    @Inject
    PlayPortal portal;
    
    @Test
    public void test1() {
        
       functional.login("tester.bmp@gmail.com", "tester123");
       
       click(header.account.myCompany);
       click(header.account.myProfile);
       click(header.account.mySettings);
       click(header.account.myCompany);
       
       click(header.account.logout);
    }
    
    @Test
    public void test2() {
       functional.login("tester.bmp@gmail.com", "tester123");
       
//       for (Link link: portal.categories ) {
//           reportMessage(link.getAttribute("title"));
//       }
       portal.category.get("Projektmanagement").click();
       
       System.out.println();
    }
}
