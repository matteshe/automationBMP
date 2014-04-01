package de.telekom.bmp.pages.account;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.*;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */

@Singleton
//@Path("/account/users")
public class InvitePopup extends Page {

    @Inject
    public InvitePopup(BmpApplication app) {
        super(app);
    } 
    
        
    // Invite popup:
    @FindBy(xpath = ".//form[@class = 'addCoWorkersForm']")
    public Form invitePopup;
    
    public static class InvitationRow extends Control {

        public InvitationRow(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }
        @FindBy(xpath = "//input[@type = 'email']")
        public TextField emailField;

    }
    
    //@FindBy(xpath= "//div[contains(@class, 'form-row')]")
    @FindBy(xpath = "//input[@type = 'email']")
    public List<InvitationRow> invitationFields;
    
    @FindBy(xpath= "//div[contains(@class, 'addAnotherEmail')]/a")
    public Link addAnotherEmailLnk;
    
    @FindBy(xpath= "//button[contains(@class, 'sendInvitations')]")
    public Button sendInvitationsBtn;
    
}
