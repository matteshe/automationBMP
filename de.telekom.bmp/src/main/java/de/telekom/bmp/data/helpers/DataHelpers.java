package de.telekom.bmp.data.helpers;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.telekom.bmp.data.Application;
import de.telekom.bmp.data.Company;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.EMailAccount;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.googlemail.data.GMailAccount;
import de.telekom.testframework.configuration.Configuration;
import org.bson.types.ObjectId;

/**
 *
 * @author Daniel
 */
public final class DataHelpers {

    Datapool datapool;

    @Inject
    public DataHelpers(Datapool datapool) {
        this.datapool = datapool;
    }

    public static class MyConfiguration extends Configuration {

        MyConfiguration() {
            initialize();
        }

        @Inject(optional = true)
        @Named("testEMailAccount.email")
        String testEMailAccountEmail = "maxtestdt@gmail.com";

        @Inject(optional = true)
        @Named("testEMailAccount.password")
        String testEMailAccountPassword = "TestA1234";

        @Inject(optional = true)
        @Named("testCompany.name")
        String testCompanyName = "t-company";

        @Inject(optional = true)
        @Named("testUser.name")
        String testUserName = "Test";
        @Inject(optional = true)
        @Named("testUser.firstName")
        String testUserFirstName = "Max";
        @Inject(optional = true)
        @Named("testUser.eMail")
        String testUserEMail = "maxtestdt@gmail.com";
        @Inject(optional = true)
        @Named("testUser.password")
        String testUserPassword = "ARandomPW1234";

        @Named("superUser.eMail")
        String superUserEMail = "testmax90+sup@gmail.com";
        @Inject(optional = true)
        @Named("superUser.password")
        String superUserPassword = "test12345";

        @Named("testApplication.name")
        String testApplicationName = "Automation_SAK1";
    }

    public static final MyConfiguration configuration = new MyConfiguration();

    public EMailAccount getTestEmailAccount() {
        EMailAccount result = datapool.emailAccounts().field(EMailAccount.Fields.email).equal(configuration.testEMailAccountEmail).get();

        if (result == null) {
            result = new GMailAccount(configuration.testEMailAccountEmail, configuration.testEMailAccountPassword);

            datapool.save(result);
        }
        return result;
    }

    protected Company defineNewTestCompany() {
        String id = ObjectId.get().toString();

        Company result = new Company(configuration.testCompanyName + "_" + id);

        return result;
    }

    public Company getTestCompany() {
        Company result = datapool.validCompanies().field(Company.Fields.name).startsWith(configuration.testCompanyName).get();
        if (result == null) {
            result = defineNewTestCompany();
            datapool.save(result);
        }

        return result;
    }

    public User defineNewTestUser() {
        return defineNewTestUser(null);
    }

    public User defineNewTestUser(String postFix) {

        String id = ObjectId.get().toString();

        User result = new User();
        if (postFix == null) {
            postFix = "";
        }

        result.name = configuration.testUserName + (postFix.isEmpty() ? "" : ("." + postFix)) + "_" + id;
        result.firstName = configuration.testUserFirstName;

        String[] splittedEmail = configuration.testUserEMail.split("@");
        result.email = splittedEmail[0] + "+" + (postFix.isEmpty() ? "" : postFix + "_") + id + "@" + splittedEmail[1];

        result.password = configuration.testUserPassword;

        result.company = getTestCompany();
        result.emailAccount = getTestEmailAccount();
        result.role = UserRole.USER;

        return result;
    }

    public User getSuperuser() {
        User result = new User();

        result.email = configuration.superUserEMail;
        result.password = configuration.superUserPassword;
        result.role = UserRole.SUPERUSER;

        return result;
    }

    public Application getTestApplication() {
        Application result = new Application();
        result.name = configuration.testApplicationName;
        return result;
    }

}
