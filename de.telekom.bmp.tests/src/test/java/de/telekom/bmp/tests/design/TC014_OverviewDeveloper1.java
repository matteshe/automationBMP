
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.developer.DeveloperCreatePage;
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
public class TC014_OverviewDeveloper1 {
    
    @Inject
    FunctionalActions functional;
            
    @Inject
    DeveloperCreatePage createProduct;
    
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(createProduct);
       verifyThat(createProduct.isCurrentPage());
       
       verifyThat(createProduct.produkteLnk.isDisplayed());
       verifyThat(createProduct.rechnungenLnk.isDisplayed());
       verifyThat(createProduct.integrationergnLnk.isDisplayed());
       verifyThat(createProduct.documentationLnk.isDisplayed());
       verifyThat(createProduct.auszahlungenLnk.isDisplayed());
       
    }
}
