package de.telekom.bmp.data;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import de.telekom.bmp.data.helpers.DataHelpers;
import de.telekom.testframework.configuration.Configuration;
import java.net.UnknownHostException;
import javax.inject.Singleton;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author Daniel
 */
@Singleton
public class Datapool {

    class MyConfiguration extends Configuration {

        MyConfiguration() {
            initialize();
        }

        @Inject(optional = true)
        @Named("BmpDatapool.host")
        public String host = "localhost";

        @Inject(optional = true)
        @Named("BmpDatapool.datastore")
        public String datastore = "bmptest";
    }

    private Morphia morphia;
    private MongoClient client;

    MyConfiguration configuration = new MyConfiguration();
    private Datastore dataStore = null;

    public Datapool() {
        this(null, null);
    }

    public Datapool(String datastore) {
        this(null, datastore);
    }

    public Datapool(String host, String datastore) {
        if (host != null) {
            configuration.host = host;
        }
        if (datastore != null) {
            configuration.datastore = datastore;
        }

        morphia = new Morphia();

        // map classes
        morphia.map(User.class);
        morphia.map(App.class);
        morphia.map(Application.class);
        morphia.map(Company.class);
        morphia.map(MailAccount.class);
        morphia.map(EMailAccount.class);

        try {
            client = new MongoClient(configuration.host);
        } catch (UnknownHostException ex) {
            throw new RuntimeException("cannot create mongo client", ex);
        }

    }

    public void dropDatastore() {
        client.dropDatabase(configuration.datastore);
    }

    public Datastore getDatastore() {
        if (dataStore == null) {

            dataStore = morphia.createDatastore(client, configuration.datastore);

            dataStore.ensureIndexes(false);
            dataStore.ensureCaps();
        }
        return dataStore;
    }

    private DataHelpers _helpers = null;

    public DataHelpers helpers() {
        if (_helpers == null) {
            _helpers = new DataHelpers(this);
        }
        return _helpers;
    }

    public Query<User> validUsers() {
        return getDatastore().find(User.class).field(User.Fields.valid).equal(true);
    }

    public Query<User> users() {
        return getDatastore().find(User.class);
    }

    public Query<App> apps() {
        return getDatastore().find(App.class);
    }

    public Query<Application> applications() {
        return getDatastore().find(Application.class);
    }

    public Query<Application> validApplications() {
        return getDatastore().find(Application.class).field(Application.Fields.valid).equal(true);
    }

    public Query<Company> companies() {
        return getDatastore().find(Company.class);
    }

    public Query<Company> validCompanies() {
        return getDatastore().find(Company.class).field(Company.Fields.valid).equal(true);
    }

    public Query<MailAccount> mailAccounts() {
        return getDatastore().find(MailAccount.class);
    }

    public Query<EMailAccount> emailAccounts() {
        return getDatastore().find(EMailAccount.class);
    }

    public <T> Key<T> save(T entity) {
        return getDatastore().save(entity);
    }

    public <T> Key<T> save(T entity, WriteConcern wc) {
        return getDatastore().save(entity, wc);
    }

}
