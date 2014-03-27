package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.MyApps;
import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice
@Test(groups = {"qcid-5506"})
public class TC003b_Login_as_Normal_User_with_active_Subscriptions {
    
    @Inject
    BmpApplication app;
    
    @Inject
    Login login;
    
    @Inject
    Home home;
    
    @Inject
    MyApps myapps;
    
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
                               .field("email").equal("mybmptestuser+normaluserwithapps@gmail.com").get();
        
        assertNotNull(user, "cannot find a valid user");
        
//        user.valid = false;
        
        navigateTo(login);
        
        // sets the englisch language
//        if (header.languageToogleEN.isDisplayed()) {
//            click(header.languageToogleEN);
//        }
      
    }
    
    @Test
    public void test_003b_Login_as_User_with_active_Subscriptions() {

        try {
            set(login.usernameTxt,user.email);

            set(login.password,user.password);

            click(login.signin);


// WORKAROUND
            navigateTo(myapps);
            
            assertThat(myapps, isCurrentPage());

            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());
            
//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }
    
    
}
