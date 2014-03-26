package de.telekom.bmp.data;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Version;
import org.bson.types.ObjectId;

/**
 *
 * @author Daniel
 */
@Entity
public class User {

    @Id
    ObjectId id;

    @Version
    long version;
    
    public String name;
    
    @Indexed(unique = true)
    public String email;
    
    public String password;
    
    public UserRole role;

    public boolean valid;
    
    // public states
    public boolean registered = false;
    
    public User() { }
    
    public User(String name, String mail, UserRole role) {
        this.name = name;
        this.email = mail;
        this.role = role;
    }
}
