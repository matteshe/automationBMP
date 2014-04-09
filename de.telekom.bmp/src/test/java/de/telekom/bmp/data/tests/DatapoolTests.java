package de.telekom.bmp.data.tests;

import static de.telekom.testframework.Assert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.sql.Timestamp;
import java.util.Date;

import org.testng.annotations.Test;

import de.telekom.bmp.data.Application;
import de.telekom.bmp.data.Company;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.testframework.Assert;

/**
 *
 * @author Daniel
 */
public class DatapoolTests {

    Datapool datapool;

    @Test
    public void setupDatapool() {
        datapool = new Datapool("UserTests");
        datapool.dropDatastore();
    }

    String timestamp = new Timestamp(new Date().getTime()).toString();
    String userName = "Test User " + timestamp;
    String email = "testuser." + timestamp + "@testemail.com";

    String companyName = "Test Company " + timestamp;
    String applicationName = "Test Application " + timestamp;

    @Test(dependsOnMethods = "setupDatapool")
    public void createCompany() {
        Company company = new Company(companyName);

        datapool.save(company);

        assertThat(datapool.companies().field("name").equal(companyName).get(),
                is(not(nullValue())));
    }

    @Test(dependsOnMethods = "setupDatapool")
    public void createCompanies() {
        datapool.save(new Company("company 1"));
        datapool.save(new Company("company 2"));
        datapool.save(new Company("company 3"));
    }

    @Test(dependsOnMethods = "setupDatapool")
    public void createUser() {
        User user = new User(userName, email);

        datapool.save(user);

        assertThat(datapool.users().field("name").equal(userName).get().email,
                is(email));
    }

    @Test(dependsOnMethods = "setupDatapool")
    public void createUsers() {
        datapool.save(new User("user 1", "user1@testmail.com"));
        datapool.save(new User("user 2", "user2@testmail.com"));
        datapool.save(new User("user 3", "user3@testmail.com"));
        datapool.save(new User("user 4", "user4@testmail.com"));
        datapool.save(new User("user 5", "user5@testmail.com"));
        datapool.save(new User("user 6", "user6@testmail.com"));
        datapool.save(new User("user 7", "user7@testmail.com"));
    }

    @Test(dependsOnMethods = {"createUser", "createCompany"})
    public void addUserToCompany() {
        User user = datapool.users().field("email").equal(email).get();
        assertThat("there is a valid user", user != null);
        user.valid = false;

        Assert.verifyThat("user has no company", user.company == null);

        try {
            user.company = datapool.companies().field("name")
                    .equal(companyName).get();

            user.valid = true;
        } finally {
            datapool.save(user);
        }
    }

    @Test(dependsOnMethods = {"createUsers", "createCompanies"})
    public void addUsersToCompany1() {
        Company company1 = datapool.companies().field("name")
                .equal("company 1").get();

        for (User user : datapool.users().field("name").startsWith("user ")
                .fetch()) {
            user.company = company1;
            datapool.save(user);
        }

        assertThat(datapool.users().field("company").equal(company1).asList(),
                is(not(empty())));
    }

    @Test(dependsOnMethods = "setupDatapool")
    public void addAUserAndACompany() {
        User user = new User("A User with a Company",
                "auserwithacompany@testmail.com");
        user.company = new Company("a company with a user");
        datapool.save(user.company);
        datapool.save(user);
    }

    @Test(dependsOnMethods = "setupDatapool")
    public void createApplication() {
        Application application = new Application(applicationName);

        datapool.save(application);

        assertThat(datapool.applications().field("name").equal(applicationName)
                .get(), is(not(nullValue())));
    }

    @Test(dependsOnMethods = "setupDatapool")
    public void createApplications() {
        datapool.save(new Application("application 1"));
        datapool.save(new Application("application 2"));
        datapool.save(new Application("application 3"));
    }

    @Test(dependsOnMethods = {"createUser", "createApplication"})
    public void addApplicationToUser() {
        User user = datapool.users().field("email").equal(email).get();
        assertThat("there is a valid user", user != null);

        Application application = datapool.applications().field("name")
                .equal(applicationName).get();
        assertThat("there is a valid application", application != null);

        user.applications.add(application);

        datapool.save(user);

        assertThat(
                datapool.users().field("applications")
                .hasThisElement(application).asList(), is(not(empty())));
        assertThat(
                datapool.users().field("applications")
                .hasThisElement(application).get(), is(user));
    }
}
