package de.telekom.bmp.pages.purchase;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Select;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */
@Singleton

@Path("/payment")
public class BillingDetails extends Page {

    @Inject
    public BillingDetails(BmpApplication app) {
        super(app);
    }
    
    
    @FindBy(xpath = "//input[@id='company-name']")
    public TextField company;
    
    @FindBy(xpath = "//select[@id='salutation']")
    public Select salutation;
    
    @FindBy(xpath = "//input[@id='first-name']")
    public TextField firstName;
    
    @FindBy(xpath = "//input[@id='last-name']")
    public TextField lastName;
    
    @FindBy(name="formInputContainer:addressBorder:addressBorder_body:address")
    public TextField address;
    
    @FindBy(xpath = "//input[@id='city']")
    public TextField city;
    
    @FindBy(xpath = "//input[@id='postal-code']")
    public TextField postCode;
    
    @FindBy(xpath = "//select[@id='countries']")
    public Select country;
    
    @FindBy(xpath = "//select[@id='state']")
    public Select state;
    
    @FindBy(xpath = "//input[@id='vat-id']")
    public TextField vatId;
    
    @FindBy (xpath = ".//button[@id= 'continue']")
    public Button nextStep;
    
    @FindBy (xpath = ".//a[@class= 'back']")
    public Button goBack;
    
    
    
}
