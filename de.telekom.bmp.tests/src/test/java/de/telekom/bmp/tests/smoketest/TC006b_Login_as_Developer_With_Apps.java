package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.DeveloperCreatePage;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MyApps;
import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@UseWebDriver
@Test(groups = {"qcid-5506"})
public class TC006b_Login_as_Developer_With_Apps {
    
    @Inject
    BmpApplication app;
    
    @Inject
    Login login;
    
    @Inject
    Home home;
    
    @Inject
    MyApps myapps;
    
    @Inject
    DeveloperCreatePage devCreatePg;
    
    @Inject
    Datapool datapool;
    
    @Inject
    Header header;

    // Needed user
    User user;
    
    @BeforeTest
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                               .field("registered").notEqual(false)
                               .field("email").equal("mybmptestuser+devwithapp@gmail.com").get();
        
        assertNotNull(user, "cannot find a valid user");
        
//        user.valid = false;
        
        navigateTo(login);
        
    }
    
    @Test
    public void test_006b_Login_as_Developer_With_Apps() {

        try {
            set(login.usernameInput,user.email);

            set(login.passwordInput,user.password);

            click(login.signinBtn);
            
            //assertThat(myapps, isCurrentPage());
            
// WORKAROUND BECAUSE OF CMS
            navigateTo(devCreatePg);
            
            assertThat(devCreatePg, isCurrentPage());
            
// WORKAROUND NORMAL BEHAVIOUR
//             
//            click(header.settings);
//            click(header.developer);


                        
            click(devCreatePg.produkteLnk);
            click(devCreatePg.rechnungenLnk);
            click(devCreatePg.integrationergnLnk);
            click(devCreatePg.documentationLnk);
            click(devCreatePg.auszahlungenLnk);
            
            // before Nested Classed introduced
            //click(header.account);
            //click(header.logout);
            
            click(header.accountMenu.logoutLnk);

            assertThat(home,isCurrentPage());
            
//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }
    
    
}
