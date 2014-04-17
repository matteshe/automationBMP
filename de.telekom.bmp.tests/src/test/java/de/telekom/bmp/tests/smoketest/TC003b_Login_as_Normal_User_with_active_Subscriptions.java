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
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@QCId("5525")
@UseWebDriver
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
    
    @BeforeMethod
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
            set(login.username,user.email);

            set(login.password,user.password);

            click(login.signin);


// WORKAROUND Because of CMS Redirect
//            navigateTo(myapps);
            
            assertThat(myapps, isCurrentPage());

            click(header.account.logout);

            assertThat(home, isCurrentPage());
            
//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }
    
    
}
