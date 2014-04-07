
package de.telekom.bmp.pages;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.controls.TextField;

/**
 *
 * @author Mathias Herkt
 */
@Singleton
@Path("/resetPassword")
public class ResetPasswordPage extends Page {

    @Inject
    public ResetPasswordPage(BmpApplication application) {
        super(application);
    }
    
    @FindBy(name = "passwordBorder:passwordBorder_body:password")    
    public TextField password;
    
    @FindBy(name = "confirmBorder:confirmBorder_body:confirmPassword")    
    public TextField confirmPassword;
    
    @FindBy(name = "resetPasswordButton")    
    public Button submitBtn;
    
    @FindBy(xpath = "//span[@class='feedbackPanelERROR']")
    public Text errorMessage;
}
