package de.telekom.bmp.tests.smoketest;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;

import de.telekom.bmp.pages.*;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.selenium.Browser;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import java.util.List;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.is;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@QCId("dummyTest")
@UseWebDriver
public class TC033_Set_Language_of_a_Logged_in_User {

    @Inject
    Datapool datapool;

    @Inject
    FunctionalActions functionalAct;
    
    @Inject
    Browser browser;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    MyApps myApps;

    @Inject
    Header header;

    @Test
    public void theTest() throws InterruptedException {
        List<User> userLst = datapool.users().asList();

        for (User user : userLst) {
            verifyThat("we have a registered user ",user.registered);
            navigateTo(login);
            String loginURL = browser.getCurrentUrl();
            set(login.username, user.email);
            set(login.password, user.password);
            click(login.signin);
            
            
//          String currentTitle = browser.getCurrentTitle();
            
            System.out.println(loginURL);
//            System.out.println(currentTitle);
            
            
            
            
            if (!(browser.getCurrentUrl().contains(loginURL))) {
                //sets the german language in the browser instance
                ensureGermLanguageIsSet();
                click(header.account.logout);
            }
            
            
            
            
        }

//        set(login.username, "tester.bmp+user001@gmail.com");
//        set(login.password, "tester123");
    }

    private void ensureGermLanguageIsSet() {
        if (header.toogle_DE_LanguageLnk.isDisplayed()) {
            click(header.toogle_DE_LanguageLnk);
        }
    }

}
