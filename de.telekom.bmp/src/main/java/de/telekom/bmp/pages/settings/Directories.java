package de.telekom.bmp.pages.settings;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.*;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */

@Singleton
@Path("/directories")
public class Directories extends Page {

    @Inject
    public Directories(BmpApplication app) {
        super(app);
    } 
    
    @FindBy(xpath = ".//table[contains(@class, 'item-table')]")
    public Form usersTable;
    
    @FindBy(xpath = ".//table[contains(@class, 'item-table')]/tbody/tr")
    public List<Link> teamMembers;
    
    @FindBy(xpath = ".//a[@class = 'jump next']")
    public Button nextPage;
    
    @FindBy(xpath = ".//a[@class = 'jump prev']")
    public Button previousPage;
    
    
    
}
