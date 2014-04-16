package de.telekom.bmp.data.helpers;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.telekom.bmp.data.Company;
import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.EMailAccount;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.testframework.configuration.Configuration;
import java.util.Date;
import java.util.UUID;
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

    protected Company createTestCompany() {
        Company result = new Company(configuration.testCompanyName);

        return result;
    }

    public Company getTestCompany() {
        Company result = datapool.companies().field(Company.Fields.name).equal(configuration.testCompanyName).get();
        if (result == null) {
            result = createTestCompany();
            datapool.save(result);
        }

        return result;
    }

    public User createTestUser() {
        return createTestUser(null);
    }

    public User createTestUser(String postFix) {
        //String id = String.valueOf(new Date().getTime());
        //String id = UUID.randomUUID().toString().replaceAll("-", "");
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
}
