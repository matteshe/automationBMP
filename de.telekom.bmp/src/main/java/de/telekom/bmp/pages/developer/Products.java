package de.telekom.bmp.pages.developer;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Form;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */
@Singleton
@Path("/cms/home")
public class Products extends Page {

    @Inject
    public Products(BmpApplication app) {
        super(app);
    }

    @FindBy(xpath = "//div[@class='intop-title']//h1")
    public Text header;
        
    @FindBy(xpath = "//button[@class='addProduct']")
    public Button addProduct;
    
    @FindBy(xpath = "//div[@id='productSummary']")
    public Text productSummary;
    
    @FindBy(xpath = ".//button[contains(@class, 'tableToggle show')]")
    public Button showFiltersBtn;
    
    @FindBy(xpath = ".//button[contains(@class, 'tableToggle hide')]")
    public Button hideFiltersBtn;
        
    @FindBy(xpath = ".//input[contains(@class, 'searchBox')]")
    public TextField searchInput;
    
    @FindBy(xpath = ".//table[contains(@class, 'item-table')]")
    public Form productTable;
    
}
