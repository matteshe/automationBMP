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
public class App {

    @Id
    ObjectId id;

    @Version
    long version;
    
    public String name;
    
    @Indexed(unique = true)
    public String testID;
    
    @Indexed(unique = true)
    public String appID;

    public App(){
        
    }
    
    public App(String name, String testID, String appID) {
        this.name = name;
        this.appID = appID;
        this.testID = testID;
    }
}
