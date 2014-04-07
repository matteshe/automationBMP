
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.account.Bills;
import de.telekom.bmp.pages.account.CompanySettings;

import static de.telekom.testframework.Actions.*;
import static de.telekom.testframework.reporting.Reporter.reportMessage;
import de.telekom.testframework.selenium.Application;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */

@UseWebDriver
public class TC013_DialogueUnternehmenseinstellungen {
    
    @Inject
    FunctionalActions functional;
            
    @Inject
    CompanySettings companySettings;
    
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(companySettings);
       verifyThat(companySettings.isCurrentPage());
       
       verifyThat(companySettings.editProfileBtn.isDisplayed());
       
       verifyThat(companySettings.companyNameInput.isDisplayed());
       
       int passwordOptions = 0;
       int roleOptions = 0;
       
       for (WebElement password: companySettings.passwordPolicies) {
           passwordOptions++;
           reportMessage(password.getText());
       }
       for (WebElement role: companySettings.roles) {
           roleOptions++;
           reportMessage(role.getText());
       }
       verifyThat(passwordOptions, is(greaterThanOrEqualTo(1)));
       verifyThat(roleOptions, is(greaterThanOrEqualTo(1)));
       
       click(companySettings.editProfileBtn);
       verifyThat( companySettings.getWebDriver().getCurrentUrl(), containsString("/companies")); 
       
       
    }
}
