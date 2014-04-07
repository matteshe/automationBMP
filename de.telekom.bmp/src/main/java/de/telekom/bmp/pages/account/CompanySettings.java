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
@Path("/account/companySettings")
public class CompanySettings extends Page {

    @Inject
    public CompanySettings(BmpApplication app) {
        super(app);
    } 
    
    @FindBy(xpath = ".//div[@class = 'intop-title']//button")
    public Button editProfileBtn;
    
    @FindBy(xpath = ".//input[contains(@name, 'companyName')]")
    public TextField companyNameInput;
    
    @FindBy(xpath = ".//input[contains(@name, 'policyGroup')]")
    public List<Select> passwordPolicies;
    
    @FindBy(xpath = ".//input[contains(@name, 'defaultRoleGroup')]")
    public List<Select> roles;
    
    @FindBy(xpath = ".//button[@name = 'companySave']")
    public Button saveBtn;
}
