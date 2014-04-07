package de.telekom.bmp.data;

import java.sql.Connection;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.telekom.testframework.configuration.Configuration;

/**
 * This class provides a connection to a MySQL database
 * @author Mathias Herkt
 */
public class MySqlDatapool {

    class MyConfiguration extends Configuration {

        MyConfiguration() {
            initialize();
        }

        @Inject(optional = true)
        @Named("mysql.host")
        public String host = "localhost";

        @Inject(optional = true)
        @Named("mysql.database")
        public String datastore = "appdirect";
        
        @Inject(optional = true)
        @Named("mysql.driver")
        public String driver = "com.mysql.jdbc.Driver";
    }

    MyConfiguration configuration = new MyConfiguration();
    private Connection dataStore = null;

    public Connection getDatastore() {
        if (dataStore == null) {

        	try {
				Class.forName(configuration.driver).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//dataStore = DriverManager.getConnection(url, userName, password);
        }
        return dataStore;
    }
}
