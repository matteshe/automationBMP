package de.telekom.bmp.data;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 *
 * @author Daniel
 */
@Entity
public class Application extends BaseEntity {

    @Indexed(unique = true)
    public String name;

    public Application() {

    }

    public Application(String name) {
        this.name = name;
    }
}
