package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Signup;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice
@QCId("123456")
public class TC002_RegistrationWithValidCredentials {

    @Inject
    BmpApplication app;

    @Inject
    Home home;

    @Inject
    Signup signup;

    @Inject
    Datapool datapool;
    
    
    // Needed user
    User user;
    

    @BeforeTest
    public void setup() {
        user = datapool.users().field("valid").equal(true).field("registered").notEqual(true).get();
        
        assertNotNull(user, "cannot find a valid user");
        
        user.valid = false;
        
        navigateTo(app);
    }

    @Test
    public void test_RegistrationWithValidCredentials() {
//       if (!user.valid) {
//            fail(user.name + "is not a valid User");
//        }
//        
        try {
            
            click(home.registerBtn);
            
            set(signup.emailAddress,user.email);
            
            assertThat("signup.iconValid.isDisplayed", signup.iconValid.isDisplayed());
            
            click(signup.signup);            
            
            user.registered = true;
            user.valid = true;
        } finally {
            
            datapool.save(user);
        }
    }
}
