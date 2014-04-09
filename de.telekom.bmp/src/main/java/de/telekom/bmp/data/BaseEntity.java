package de.telekom.bmp.data;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Version;
import java.util.Date;
import java.util.Objects;
import org.bson.types.ObjectId;

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
