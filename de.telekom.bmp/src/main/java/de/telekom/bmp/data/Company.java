package de.telekom.bmp.data;

import com.google.code.morphia.annotations.Entity;

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
