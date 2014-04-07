package de.telekom.bmp.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.telekom.testframework.Assert;
import de.telekom.testframework.configuration.Configuration;

/**
 * This class provides a connection to a MySQL database
 * 
 * @author Mathias Herkt
 */
public class MySqlDatapool {
	private static final Logger LOG = Logger.getLogger(MySqlDatapool.class
			.getSimpleName());

	class MyConfiguration extends Configuration {

		MyConfiguration() {
			initialize();
		}

		@Inject(optional = true)
		@Named("mysql.host")
		public String host = "localhost";

		@Inject(optional = true)
		@Named("mysql.port")
		public short port = 3306;

		@Inject(optional = true)
		@Named("mysql.database")
		public String datastore = "appdirect";

		@Inject(optional = true)
		@Named("mysql.uname")
		public String uname = "root";

		@Inject(optional = true)
		@Named("mysql.password")
		public String password = "";

		@Inject(optional = true)
		@Named("mysql.driver")
		public String driver = "com.mysql.jdbc.Driver";
	}

	MyConfiguration conf = new MyConfiguration();
	private Connection dataStore = null;

	public Connection getDatastore() {
		if (dataStore == null) {

			try {
				Class.forName(conf.driver).newInstance();

				StringBuilder url = new StringBuilder();
				url.append("jdbc:mysql://");
				url.append(conf.host).append(":");
				url.append(conf.port).append("/");
				url.append(conf.datastore);

				dataStore = DriverManager.getConnection(url.toString(),
						conf.uname, conf.password);
			} catch (InstantiationException e) {
				LOG.severe("Problem to instanciate the mysql driver.\n"
						+ e.getMessage());
			} catch (IllegalAccessException e) {
				LOG.severe("Database Access problems.\n" + e.getMessage());
			} catch (ClassNotFoundException e) {
				LOG.severe("Couln't find driver class.\n" + e.getMessage());
			} catch (SQLException e) {
				LOG.severe("Probelm to get the connection.\n" + e.getMessage());
			}
		}
		Assert.assertThat("MySQL database connection should be established",
				dataStore != null);
		return dataStore;
	}

	public void close() {
		try {
			if (this.dataStore != null) {
				this.dataStore.close();
			}
		} catch (SQLException e) {
			LOG.warning("Problem to close db connection.\n" + e.getMessage());
		}
	}
}
