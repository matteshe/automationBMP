package de.telekom.bmp.pages.cms;
            
import de.telekom.bmp.pages.cms.*;
import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
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
 * @author Jan
 */
@Singleton

@Path("/cms/payoutdetails")
public class PayoutDetails extends Page {

    @Inject
    public PayoutDetails(BmpApplication app) {
        super(app);
    }
    
    @FindBy(name = "emailBorder:emailBorder_body:email")
    public TextField emailTxt;
    
    @FindBy (name = "streetBorder:streetBorder_body:address")
    public TextField streetTxt;
     
    @FindBy (name = "cityBorder:cityBorder_body:city")
    public TextField cityTxt;

    @FindBy (name="countryBorder:countryBorder_body:countryDropdownPanel:countries")
    public List<Select> countryList;
    
    //@FindBy (xpath = "//select[contains(@name, 'countryDropdownPanel') and contains(@name, 'countries')")

//    @FindBy(name = "formInputContainer:countryBorder:countryBorder_body:countryDropdownPanel:countries")
//    public Select country;

    @FindBy (name = "statePanel:stateText")
    public TextField stateTxt;

    @FindBy (name = "postalCodeBorder:postalCodeBorder_body:postalCode")
    public TextField postalCodeTxt;

    @FindBy (name = "vatIdBorder:vatIdBorder_body:vatId")
    public TextField vatIdTxt;
    
    @FindBy (name = "accountHolderBorder:accountHolderBorder_body:accountHolder")
    public TextField accountHolderTxt;

    @FindBy (name = "bankCodeBorder:bankCodeBorder_body:bankCode")
    public TextField bankCodeTxt;
    
    @FindBy (name = "accountNumberBorder:accountNumberBorder_body:accountNumber")
    public TextField accountNoTxt;
    
    @FindBy (name = "ibanBorder:ibanBorder_body:iban")
    public TextField ibanTxt;
    
    @FindBy (name = "swiftBorder:swiftBorder_body:swift")
    public TextField swiftTxt;

    @FindBy (name = "bankAccountCountryBorder:bankAccountCountryBorder_body:bankAccountCountry:countries")
    public List<Select> bankAccountCountryList;
//
    
    @FindBy (xpath = "//button[contains(text(), 'Speichern')]")
    public Button saveBtn;
  
}
