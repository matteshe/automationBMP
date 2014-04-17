package de.telekom.bmp.data;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 *
 * @author Daniel
 */
@Entity("EMailAccount")
public abstract class EMailAccount extends BaseEntity {

    public static class Fields extends BaseEntity.Fields {

        public static final String email = "email";
    }

    @Indexed(unique = true)
    public String email;

    public EMailAccount() {
    }

    public EMailAccount(String email) {
        this.email = email;
    }

    public static String getConfirmationLink(User user) {
        return user.emailAccount.internalGetConfirmationLink(user);
    }

    protected abstract String internalGetConfirmationLink(User user);

    public static String getInvitationsLink(User user) {
        return user.emailAccount.internalGetInvitationsLink(user);
    }

    protected abstract String internalGetInvitationsLink(User user);
}
