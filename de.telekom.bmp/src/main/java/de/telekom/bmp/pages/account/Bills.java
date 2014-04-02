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
@Path("/account/bills")
public class Bills extends Page {

    @Inject
    public Bills(BmpApplication app) {
        super(app);
    } 
    
    @FindBy(xpath = ".//button[contains(@class, 'tableToggle show')]")
    public Button showFiltersBtn;
    
    @FindBy(xpath = ".//button[contains(@class, 'tableToggle hide')]")
    public Button hideFiltersBtn;
        
    @FindBy(xpath = ".//input[contains(@class, 'searchBox')]")
    public TextField searchInput;
    
    @FindBy(xpath = ".//table[contains(@class, 'item-table')]")
    public Form billsTable;
    
    @FindBy(xpath = ".//div[contains(@class, 'accountMenu')]//li[3]/a")
    public Link payments;
    
    @FindBy(xpath = ".//div[contains(@class, 'accountMenu')]//li[4]/a")
    public Form paymentDetails;
    
    @FindBy(xpath = ".//div[contains(@class, 'dropdown')]")
    public List<Select> dropdownFilter;
    
    @FindBy(xpath = ".//div[contains(@class, 'checkbox')]")
    public List<Select> checkboxFilter;
    
    @FindBy(xpath = ".//div[contains(@class, 'time')]")
    public List<Select> timeFilter;
    
    
}
