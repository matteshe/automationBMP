package de.telekom.bmp.data;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 * @author Mathias Herkt
 *
 */
@Entity
@Deprecated
public class MailAccount extends BaseEntity {

    @Indexed(unique = true)
    public String provider;

    @Indexed(unique = true)
    public String mailAddress;

    public String password;
}
