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
        @Named("testEMailAccountEmail")
        String testEMailAccountEmail = "mybmptestuser@gmail.com";

        @Inject(optional = true)
        @Named("testEMailAccountPassword")
        String testEMailAccountPassword = "galerien3?";

        @Inject(optional = true)
        @Named("testCompanyName")
        String testCompanyName = "testcompany";

        @Inject(optional = true)
        @Named("testUserName")
        String testUserName = "Tester";
        @Inject(optional = true)
        @Named("testUserFirstName")
        String testUserFirstName = "Max";
        @Inject(optional = true)
        @Named("testUserEMail")
        String testUserEMail = "mybmptestuser@gmail.com";
        @Inject(optional = true)
        @Named("testUserPassword")
        String testUserPassword = "Abcdefg1234";

        @Named("superUserEMail")
        String superUserEMail = "testmax90+sup@gmail.com";
        @Inject(optional = true)
        @Named("superUserPassword")
        String superUserPassword = "test12345";

        @Named("testApplicationName")
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
