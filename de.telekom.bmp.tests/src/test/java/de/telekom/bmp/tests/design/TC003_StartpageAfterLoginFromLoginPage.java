
package de.telekom.bmp.tests.design;

import de.telekom.bmp.data.Datapool;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MyApps;

import static de.telekom.testframework.Actions.*;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.containsString;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */

@UseWebDriver
public class TC003_StartpageAfterLoginFromLoginPage {
    
    @Inject
    FunctionalActions functional;
    
    @Inject 
    Datapool datapool;
    
    @Inject 
    Header header;
       
    @Inject 
    MyApps launchpad;
    
    @Inject
    Home home;
        
    @Inject
    Login login;
    
    @Test
    public void test1() throws InterruptedException {
        
        functional.login("sysadmin4.0@test.com", "password");
        verifyThat(home.getWebDriver().getCurrentUrl(), containsString("portal.bmptest.de")); 
       
       System.out.println();
    }
}
