package de.telekom.bmp.tests.tcs_07_channel.core_functions;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.channel.MarketPlacePage;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.Assert;
import static de.telekom.testframework.Assert.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

@QCId("5615")
@UseWebDriver
public class TC01_Verify_Publish_Approve_apps_function_ChanAdmin {

    @Inject
    BmpApplication app;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    MarketPlacePage marketplacepage;

    @Inject
    Datapool datapool;
    
    @Inject
    FunctionalActions fa;

    @Inject
    Header header;

    // Needed user
    User user;

    @BeforeMethod
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                .field("registered").notEqual(false)
                .field("email").equal("mybmptestuser+chanadmin@gmail.com").get();

        assertNotNull(user, "cannot find a valid user");

//        user.valid = false;
        navigateTo(login);

    }

    @Test
    public void theTest() {

        try {
            fa.login(user.email, user.password);

            click(header.settingsMenu.channelUserLnk);


// WORKAROUND
//            navigateTo(marketplacepage);
            
            
            assertThat(marketplacepage, isCurrentPage());

            click(marketplacepage.productsTab);
            
            Assert.fail("Test Steps are missing!!!- TODO");       
            
            
            
            
            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
