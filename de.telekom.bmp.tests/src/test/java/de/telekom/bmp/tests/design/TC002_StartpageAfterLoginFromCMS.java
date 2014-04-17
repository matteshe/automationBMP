
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
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */

@UseWebDriver
public class TC002_StartpageAfterLoginFromCMS {
    
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
        
        navigateTo(login);
        navigateTo(home);
        click(header.login);
        verifyThat(login.isCurrentPage());
        set(login.username, "sysadmin4.0@test.com");
        set(login.password, "password");
        click(login.signin);
        verifyThat(launchpad.isCurrentPage());
        
              
       
       System.out.println();
    }
}
