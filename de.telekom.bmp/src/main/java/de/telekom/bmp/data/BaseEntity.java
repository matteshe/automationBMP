package de.telekom.bmp.data;

import java.util.Date;
import java.util.Objects;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;

/**
 *
 * @author Daniel
 */
public abstract class BaseEntity {

    @Id
    protected ObjectId id;

    @Version
    protected long version;

    protected Date lastUpdated = new Date();

    public static class Fields {

        public static final String valid = "valid";
    }

    public boolean valid = true;

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return id.compareTo(((User) obj).id) == 0;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

}
