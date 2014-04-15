package de.telekom.bmp.tests.purchasetest;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.functional.BookingActions;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */
@UseWebDriver
public class TC003_BookTrialEdition {

    @Inject
    FunctionalActions functional;
        
    @Inject
    Datapool datapool;
    
    @Inject
    BookingActions bookingActions;

    
    @Test
    public void test(){
    
        
//        App app = datapool.apps().field("testID").equal("3787").get();
//
//        assertThat("we have a valid App", app != null);
//
//        User user = datapool.users()
//                .field("valid").equal(true)
//                .field("registered").equal(true)
//                .field("role").equal(UserRole.USER)
//                //                .field("name").equal("NormalUserWithApps")
//                .field("apps").notEqual(app.appID).get();
//
//        assertThat("we have a valid user", user != null);
//
//        functional.login(user.email, user.password);
//
//        System.out.println("----" + app.appID + "----" + app.name + "----" + app.testID + "----");

        functional.login("mybmptestuser+1397044329070@gmail.com", "12345!QAY");
        bookingActions.createOrder("1260", "Trial");
        bookingActions.confirmOrder();
        bookingActions.orderReceipt();
                
    }

}
