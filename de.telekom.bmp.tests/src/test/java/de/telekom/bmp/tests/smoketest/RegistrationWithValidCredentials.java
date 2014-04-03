package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Signup;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UseWebDriver
@QCId("123456")
public class RegistrationWithValidCredentials {

    @Inject
    BmpApplication app;

    @Inject
    Home home;

    @Inject
    Signup signup;

    @Inject
    Datapool datapool;

    @BeforeMethod
    public void setup() {
        app.navigateTo();
    }

    @Test
    public void theTest() {
        User user = datapool.users().field("valid").equal(true).field("registered").notEqual(true).get();
        assertThat("cannot find a valid user", user != null);        
        user.valid = false;        
        
        try {
            
            click(home.registerBtn);
            
            set(signup.emailAddress, user.email);
            assertThat(signup.iconValid, not(is(not(displayed()))));
            
            click(signup.signup);            
            
            user.registered = true;
            user.valid = true;
        } finally {            
            datapool.save(user);
        }
    }
}
