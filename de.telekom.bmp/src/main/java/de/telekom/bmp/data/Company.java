package de.telekom.bmp.data;
import org.mongodb.morphia.annotations.Entity;

/**
 *
 * @author Daniel
 */
@Entity
public class Company extends BaseEntity {

    public String name;

    public Company() {

    }

    public Company(String name) {
        this.name = name;
    }
}
