package de.telekom.bmp.pages;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.TextField;

/**
 * 
 * @author Mathias Herkt
 *
 */
@Singleton
@Path("/invitations")
public class InvitationPage extends Page {

	@Inject
	public InvitationPage(BmpApplication application) {
		super(application);
	}
	
	@FindBy(name = "firstName")
	public TextField firstNameInput;
	
	@FindBy(name = "lastName")
	public TextField lastNameInput;
	
	@FindBy(name = "passwordBorder:password")
	public TextField passwordInput;
	
	@FindBy(name = "confirmPassword")
	public TextField confirmPasswordInput;

	@FindBy(name = "register")
	public Button submit;
}
