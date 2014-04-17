package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.pages.CreateFreeTrialPage;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.FindApplicationsPage;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UseWebDriver
//@Test(groups = {"qcid-5506"})
@QCId("4133")
public class TC015_Start_a_free_Trial {

    @Inject
    BmpApplication app;

    @Inject
    Login login;

    @Inject
    Home home;

    @Inject
    FindApplicationsPage findAppsPage;

    @Inject
    CreateFreeTrialPage createFreeTrialPage;

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
    public void test_015_Start_a_free_Trial() {

        try {
            //set(login.username, user.email);
            //set(login.password, user.password);
            
            set(login.username, "dtagtester01+reg138a@googlemail.com");
            set(login.password, "baum1301");
            click(login.signin);

            //TODO configure search Text. Properties File?
            set(header.searchInput, "create");
            click(header.searchBtn);

            assertThat(findAppsPage, isCurrentPage());

            click(createFreeTrialPage.createFreeTrialAsdfLnk);
            click(createFreeTrialPage.pricingLnk);
            click(createFreeTrialPage.try3DaysBtn);
            click(createFreeTrialPage.continueBtn);
            click(createFreeTrialPage.placeOrderBtn);
            click(createFreeTrialPage.goToMyAppsBtn);

            //assertThat(home, page());
            //click(marketplacepage.evenlogsTab);
            // before Nested Classed introduced
            //click(header.account);
            //click(header.logout);
            
            click(header.accountMenu.logout);

            assertThat(home, isCurrentPage());

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
