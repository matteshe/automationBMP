package de.telekom.bmp.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;

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

	public boolean valid;

	// public states
	public boolean registered = false;

	public Set<String> apps = new HashSet<>();

	@Reference
    public Company company;

    @Reference
    public Set<Application> applications = new HashSet<>();

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

	/**
	 * Create a user object with some default data
	 * 
	 * @return a user instance
	 */
	public static User createUser(String mailPrefix) {
		long postfixIdentifier = createPostfixId();
		User newUser = new User();

		newUser.email = createMailAlias(mailPrefix, postfixIdentifier);
		newUser.password = "12345!QAY";
		newUser.firstName = addIdentifier("max", postfixIdentifier);
		newUser.name = addIdentifier("mustermann", postfixIdentifier);
		newUser.companyName = addIdentifier("companyName", postfixIdentifier);
		newUser.registered = false;
		newUser.role = UserRole.USER;
		newUser.valid = false;

		return newUser;
	}

	private static String addIdentifier(String value, long identifier) {
		return value + "+" + identifier;
	}

	private static String createMailAlias(String mailPrefix, long identifier) {
		if ("".equals(mailPrefix)) {
			mailPrefix = "mybmptestuser";
		}
		return addIdentifier(mailPrefix, identifier) + "@gmail.com";
	}

	private static long createPostfixId() {
		return (new Date()).getTime();
	}
}
