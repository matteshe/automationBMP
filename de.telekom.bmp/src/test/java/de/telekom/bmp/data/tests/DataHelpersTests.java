package de.telekom.bmp.data.tests;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.helpers.DataHelpers;
import static de.telekom.testframework.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
public class DataHelpersTests {

    Datapool datapool;
    DataHelpers dataHelpers;

    @Test
    public void setupDatapool() {
        datapool = new Datapool("UserTests");
        datapool.dropDatastore();

        dataHelpers = new DataHelpers(datapool);
    }

    User testUser;

    @Test(dependsOnMethods = "setupDatapool")
    public void createTestUser() {
        testUser = dataHelpers.createTestUser();
        datapool.save(testUser);
    }

    @Test(dependsOnMethods = "createTestUser")
    public void findTestUser() {
        User user = datapool.validUsers().field(User.Fields.email).equal(testUser.email).get();
        assertThat(user != null);
    }

    @Test(dependsOnMethods = "createTestUser")
    public void createASecondTestUser() {
        User user = dataHelpers.createTestUser();
        datapool.save(user);

        user = datapool.validUsers().field(User.Fields.email).equal(user.email).get();
        assertThat(user != null);
    }
    
    @Test(dependsOnMethods = "setupDatapool")
    public void createTestUserWithPostFix() {
        testUser = dataHelpers.createTestUser("user");
        datapool.save(testUser);
    }
    
    @Test(dependsOnMethods = "createTestUserWithPostFix")
    public void createAnotherTestUserWithPostFix() {
        testUser = dataHelpers.createTestUser("user");
        datapool.save(testUser);
    }

}
