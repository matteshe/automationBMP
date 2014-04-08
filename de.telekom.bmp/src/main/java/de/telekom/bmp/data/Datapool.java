package de.telekom.bmp.data;

import java.net.UnknownHostException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

import de.telekom.testframework.configuration.Configuration;

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
		@Named("BmpDatapool.host")
		// TODO 20140407 this property is probably wrong?
		public String datastore = "bmptest";
	}

	MyConfiguration configuration = new MyConfiguration();
	private Datastore dataStore = null;

	public Datastore getDatastore() {
		if (dataStore == null) {

			Morphia morphia = new Morphia();

			// map classes
			morphia.map(User.class);
			morphia.map(App.class);

			try {
				MongoClient client = new MongoClient(configuration.host);

				dataStore = morphia.createDatastore(client,
						configuration.datastore);
			} catch (UnknownHostException ex) {
				throw new RuntimeException("cannot create datastore", ex);
			}

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

	public Query<ApplicationInfo> appInfos() {
		return getDatastore().find(ApplicationInfo.class);
	}

	public Query<MailAccount> mailAccounts() {
		return getDatastore().find(MailAccount.class);
	}

	public <T> Key<T> save(T entity) {
		return getDatastore().save(entity);
	}

	public <T> Key<T> save(T entity, WriteConcern wc) {
		return getDatastore().save(entity, wc);
	}
}
