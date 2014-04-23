package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.CreateFreeTrialPage;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.Login;
import de.telekom.bmp.pages.FindApplicationsPage;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import static de.telekom.testframework.selenium.Matchers.*;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Pierre Nomo
 */

@UseWebDriver
//@Test(groups = {"qcid-5506"})
@QCId(value = "4133", state = QCState.Ongoing)
public class TC015_Start_a_free_Trial {

    @Inject
    BmpApplication app;
    
    @Inject
    FunctionalActions fa;

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
            fa.login(user);

            //TODO configure search Text. Properties File?
            set(header.searchInput, "create");
            click(header.searchBtn);

            assertThat(findAppsPage, is(currentPage()));

            click(createFreeTrialPage.createFreeTrialAsdfLnk);
            click(createFreeTrialPage.pricingLnk);
            click(createFreeTrialPage.try3DaysBtn);
            click(createFreeTrialPage.continueBtn);
            click(createFreeTrialPage.placeOrderBtn);
            click(createFreeTrialPage.goToMyAppsBtn);

            fa.logout();

            assertThat(home, is(currentPage()));

//            user.valid = true;
        } finally {
            user.registered = true;
            datapool.save(user);
        }

    }

}
