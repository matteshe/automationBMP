package de.telekom.bmp.data;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import de.telekom.testframework.configuration.Configuration;
import java.net.UnknownHostException;

/**
 *
 * @author Daniel
 */
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

        try {
            client = new MongoClient(configuration.host);

            dataStore = morphia.createDatastore(client, configuration.datastore);
        } catch (UnknownHostException ex) {
            throw new RuntimeException("cannot create morphia datastore", ex);
        }

    }

    public void dropDatastore() {
        client.dropDatabase(configuration.datastore);
    }

    public Datastore getDatastore() {
        if (dataStore == null) {

            dataStore = morphia.createDatastore(client, configuration.datastore);

            dataStore.ensureIndexes();
            dataStore.ensureCaps();
        }
        return dataStore;
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

    public Query<Company> companies() {
        return getDatastore().find(Company.class);
    }

    public <T> Key<T> save(T entity) {
        return getDatastore().save(entity);
    }

    public <T> Key<T> save(T entity, WriteConcern wc) {
        return getDatastore().save(entity, wc);
    }

}
