package de.telekom.bmp.pages.account;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */

@Singleton
@Path("/account/roles")
public class Roles extends Page {

    @Inject
    public Roles(BmpApplication app) {
        super(app);
    } 
    
        
    @FindBy(xpath = ".//div[contains(@class, 'users')]//table")
    public Form usersTable;
    
    @FindBy(xpath = ".//div[contains(@class, 'assignRight')]")
    public Form assignRolesTable;
    
    @FindBy(xpath = ".//input[contains(@class, 'userSearch')]")
    public TextField searchInput;
    
    
}
