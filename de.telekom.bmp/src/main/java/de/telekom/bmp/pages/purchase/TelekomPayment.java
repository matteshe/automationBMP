package de.telekom.bmp.pages.purchase;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */
@Singleton
@Path("")
public class TelekomPayment extends Page {

    @Inject
    public TelekomPayment(BmpApplication app) {
        super(app);
    }

    @FindBy(xpath = "//input[@id='usr1']")
    public TextField usernameTextF;

    @FindBy(xpath = "//input[@id='pwd1']")
    public TextField passwordTextF;
    
    @FindBy(xpath = "//button[@type='submit']")
    public Button loginBtn;

}
