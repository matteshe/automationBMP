package de.telekom.bmp.data;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 *
 * @author Daniel
 */
@Entity
public class App extends BaseEntity {

    public String name;

    @Indexed(unique = true)
    public String testID;

    @Indexed(unique = true)
    public String appID;

    public App() {

    }

    public App(String name, String testID, String appID) {
        this.name = name;
        this.appID = appID;
        this.testID = testID;
    }
}
