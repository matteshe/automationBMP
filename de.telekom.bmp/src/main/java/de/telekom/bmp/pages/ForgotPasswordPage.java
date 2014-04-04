
package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Mathias Herkt
 */
@Singleton
@Path("/forgotPassword")
public class ForgotPasswordPage extends Page {

    @Inject
    public ForgotPasswordPage(BmpApplication app) {
        super(app);
    }
    
    @FindBy (name = "emailborder:emailborder_body:email")
    public TextField email;
    
    @FindBy (name = "sendButton")
    public TextField sendMailBtn;
}
