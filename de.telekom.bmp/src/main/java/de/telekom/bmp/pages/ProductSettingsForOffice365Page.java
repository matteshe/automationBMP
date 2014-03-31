package de.telekom.bmp.pages;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Option;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
public class ProductSettingsForOffice365Page extends Page {

    @Inject
    public ProductSettingsForOffice365Page(BmpApplication app) {
        super(app);
    }

    @Override
    public String getPath() {
        return "/checkout/settings";
    }
    
    @FindBy(xpath = "//*[@id='mosiDomain']")
    public TextField mosiDomainInput;
    
    @FindBy(xpath = "//button[@id='subContinue']")
    public Button continueBtn;
 
    @FindBy(name = "line1")
    public TextField line1Input;
 
    @FindBy(name ="line2")
    public TextField line2Input;

    @Override
    public String toString() {
        return super.toString() + getPath();
    }
 
    @FindBy(name = "postalCode")
    public TextField postalCodeInput;
 
    @FindBy(name = "city")
    public TextField cityInput;
    
    @FindBy(name = "stateBorder:stateBorder_body:statePanel:state")
    public Option provinceCbox;
    
    @FindBy(name = "phoneNumber")
    public TextField phoneNumberInput;
    
    @FindBy(xpath = "//button[@class='account-submit create buttonResponse'")
    public Button continueStep2Btn;   
    
 
    @FindBy(xpath = "//div[@id='header']//div[@class='account']")
    public Button account;
 
    @FindBy(xpath = "//a[@id='logout']")
    public Link logout;    
    
}
