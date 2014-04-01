package de.telekom.bmp.pages.account;

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
@Path("/account/apps")
public class Applications extends Page {

    @Inject
    public Applications(BmpApplication app) {
        super(app);
    } 
    
        
    @FindBy(xpath = ".//button[contains(@class, 'tableToggle show')]")
    public Button showFiltersBtn;
    
    @FindBy(xpath = ".//button[contains(@class, 'tableToggle hide')]")
    public Button hideFiltersBtn;
    
    @FindBy(xpath = ".//input[contains(@class, 'searchBox')]")
    public TextField searchInput;
    
    @FindBy(xpath = ".//table[contains(@class, 'tableFilters')]")
    public Form filtersTable;
    
    @FindBy(xpath = "//div[@class = 'filter dropdown']")
    public List<Select> appFilters;
    
    
    
}
