/**
 * 
 */
package de.telekom.bmp.data;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

/**
 * @author Mathias Herkt
 * 
 */
@Entity
public class MailAccount {
	@Id
	private ObjectId id;

	@Indexed(unique = true)
	private String provider;

	@Indexed(unique = true)
	private String mailAddress;

	private String password;

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
