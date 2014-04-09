package de.telekom.bmp.data;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;

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
