package de.telekom.bmp.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.PostLoad;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author Daniel
 */
@Entity
public class User extends BaseEntity {

    public static class Fields extends BaseEntity.Fields {

        public static final String name = "name";
        public static final String firstName = "firstName";
        public static final String companyName = "companyName";
        public static final String email = "email";
        public static final String password = "password";

        public static final String role = "role";

        public static final String registered = "registered";
        public static final String invited = "invited";

        public static final String company = "company";
        public static final String phoneNumber = "phoneNumber";

        public static final String applications = "applications";
        public static final String emailAccount = "emailAccount";
    }

    public String name;
    public String firstName;
    public String phoneNumber;

    @Deprecated
    public String companyName;

    @Indexed(unique = true)
    public String email;

    public String password;
    public UserRole role = UserRole.UNKNOWN;

    @Reference
    public EMailAccount emailAccount;

    public boolean registered = false;
    public boolean invited;

    @Deprecated
    public Set<String> apps = new HashSet<>();

    @Reference
    public Company company;

    @Reference
    public Set<Application> applications = new HashSet<>();

    @PostLoad
    protected void postload() {
        if (applications == null) {
            applications = new HashSet<>();
        }
    }

    public User() {
    }

    public User(String name, String mail, UserRole role) {
        this.name = name;
        this.email = mail;
        this.role = role;
    }

    public User(String name, String mail) {
        this(name, mail, UserRole.USER);
    }

    @Override
    public String toString() {
        return "User[email=" + email + ", firstName=" + firstName + ", name=" + name + "]";
    }

    @Deprecated
    public static User createUser() {
        return createUser(null);
    }

    /**
     * Create a user object with some default data
     *
     * @param mailPrefix
     * @return a user instance
     */
    @Deprecated
    public static User createUser(String mailPrefix) {
        long postfixIdentifier = createPostfixId();
        User newUser = new User();

        newUser.email = createMailAlias(mailPrefix, postfixIdentifier);
        newUser.password = "12345!QAY";
        newUser.firstName = addIdentifier("max", postfixIdentifier);
        newUser.name = addIdentifier("mustermann", postfixIdentifier);
        newUser.companyName = addIdentifier("testcompany", postfixIdentifier);
        newUser.registered = false;
        newUser.role = UserRole.USER;

        return newUser;
    }

    private static String addIdentifier(String value, long identifier) {
        return value + "+" + identifier;
    }

    private static String createMailAlias(String mailPrefix, long identifier) {
        if (mailPrefix == null || mailPrefix.isEmpty()) {
            mailPrefix = "mybmptestuser";
        }
        return addIdentifier(mailPrefix, identifier) + "@gmail.com";
    }

    private static long createPostfixId() {
        return new Date().getTime();
    }
}
