package de.telekom.bmp.data;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Version;
import java.util.Set;
import java.util.TreeSet;
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
    
    public Set<String> apps = new TreeSet<>();
    
    public User() { }
    
    public User(String name, String mail, UserRole role) {
        this.name = name;
        this.email = mail;
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public void setApps(Set<String> apps) {
        this.apps = apps;
    }
    
    
}
