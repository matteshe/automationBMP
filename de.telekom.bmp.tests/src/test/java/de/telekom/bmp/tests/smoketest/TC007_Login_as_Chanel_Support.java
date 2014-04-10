package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;

import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.channel.MarketPlacePage;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@QCId("4126")
@UseWebDriver
public class TC007_Login_as_Chanel_Support {
    
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
    Header header;

    // Needed user
    User user;
    
    @BeforeMethod
    public void setup() {
        user = datapool.users().field("valid").equal(true)
                               .field("registered").notEqual(false)
                               .field("email").equal("mybmptestuser+chansupport@gmail.com").get();
        
        assertNotNull(user, "cannot find a valid user");
        
//        user.valid = false;
        
        navigateTo(login);
        
    }
    
    @Test
    public void test_007_Login_as_ChannelSupport() {

        try {
            set(login.usernameInput, user.email);

            set(login.passwordInput,user.password);

            click(login.signinBtn);
            
            click(header.settingsMenu.channelUserLnk);
            
// WORKAROUND BECAUSE OF CMS Redirect
//            navigateTo(marketplacepage);
            
            assertThat(marketplacepage,isCurrentPage());
            
            click(marketplacepage.evenlogsTab);            
            
            click(header.accountMenu.logoutLnk);

            assertThat(home, isCurrentPage());
            
//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }
    
    
}
