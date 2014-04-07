package de.telekom.bmp.tests.smoketest;

import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Assert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.AccountHandling;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.annotations.UseWebDriver;

@UseWebDriver
@QCId("5506")
public class TC002_RegistrationWithValidCredentials {
	private static final String MAIL_PREFIX = "mybmptestuser";
    @Inject
    AccountHandling accountHandling;
    
    @Inject
    BmpApplication app;    

    @Inject
    Datapool datapool;

    @BeforeTest
    public void setup() {
        navigateTo(app);
    }

    @AfterTest
    public void tearDown() {
    }

    @Test
    public void registrationWithValidCredentials() throws InterruptedException {
    	// create a valid and not registered user
        User user = User.createUser(MAIL_PREFIX);
        assertThat(user, notNullValue());
        
    	accountHandling.registerAccount(user);
        user.registered = true;
        
        // save in testdata db
        datapool.save(user);
    }
}
