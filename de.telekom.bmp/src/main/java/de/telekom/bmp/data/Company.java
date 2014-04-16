package de.telekom.bmp.data;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 *
 * @author Daniel
 */
@Entity
public class Company extends BaseEntity {

    public static class Fields extends BaseEntity.Fields {

        public static final String name = "name";
    }
    
    @Indexed(unique = true)
    public String name;

    public Company() {

    }

    public Company(String name) {
        this.name = name;
    }
}
