/**
 * 
 */
package de.telekom.bmp.data;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * @author Mathias Herkt
 * 
 */
@Entity
public class ApplicationInfo {
	@Id
	private ObjectId id;

	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
