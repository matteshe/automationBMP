package de.telekom.bmp.data;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Version;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.bson.types.ObjectId;

/**
 *
 * @author Daniel
 */
@Entity
public class User extends BaseEntity {

    public String name;

    public String firstName;

    public String companyName;

    @Indexed(unique = true)
    public String email;

    public String password;

    public UserRole role;

    // public states
    public boolean registered = false;

    public Set<String> apps = new HashSet<>();

    @Reference
    public Company company;

    @Reference
    public Set<Application> applications = new HashSet<>();

    public User() {
    }

    public User(String name, String mail) {
        this(name, mail, UserRole.UNKNOWN);
    }
    
    public User(String name, String mail, UserRole role) {
        this.name = name;
        this.email = mail;
        this.role = role;
    }

    /**
     * Create a user object with some default data
     *
     * @return a user instance
     */
    public static User createUser(String mailPrefix) {
        User newUser = new User();
        newUser.email = createMailAlias(mailPrefix);
        newUser.password = "12345!QAY";
        newUser.firstName = "max";
        newUser.name = "mustermann";
        newUser.companyName = "companyName";
        newUser.registered = false;
        newUser.valid = false;

        return newUser;
    }

    private static String createMailAlias(String mailPrefix) {
        if ("".equals(mailPrefix)) {
            mailPrefix = "mybmptestuser";
        }
        return mailPrefix + "+" + createMailAlias() + "@gmail.com";
    }

    private static long createMailAlias() {
        return (new Date()).getTime();
    }
}
