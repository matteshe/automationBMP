package de.telekom.bmp.pages.account;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */

@Singleton
@Path("/account/users")
public class Users extends Page {

    @Inject
    public Users(BmpApplication app) {
        super(app);
    } 
    
        
    @FindBy(xpath = ".//button[contains(@class, 'addProduct')]")
    public Button inviteUsersBtn;
    
    @FindBy(xpath = ".//button[contains(@class, 'manage-roles')]")
    public Button manageRolesBtn;
    
    @FindBy(xpath = ".//input[contains(@class, 'searchBox')]")
    public TextField searchInput;
    
    @FindBy(xpath = ".//table[contains(@class, 'item-table')]")
    public Form usersTable;
    
    
    
    // Invite popup:
    @FindBy(xpath = ".//form[@class = 'addCoWorkersForm']")
    public Form invitePopup;
}
