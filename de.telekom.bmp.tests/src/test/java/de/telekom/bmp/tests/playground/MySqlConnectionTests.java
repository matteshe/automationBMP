/**
 * 
 */
package de.telekom.bmp.tests.playground;

import java.sql.Connection;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.telekom.bmp.data.MySqlDatapool;
import de.telekom.testframework.Assert;

/**
 * This class uses the MySqlDatapool and the generated jooq database API
 * @author Mathias Herkt
 *
 */
public class MySqlConnectionTests {
	MySqlDatapool db;
	
	@BeforeMethod
	public void setup() {
		db = new MySqlDatapool();
	}
	
	@AfterMethod
	public void tearDown() {
		db.close();
	}
	
	
	@Test
	public void openDatabaseConnection() {
		Connection conn = db.getDatastore();
		Assert.assertThat("Connection must not be null", conn != null);
	}

}
