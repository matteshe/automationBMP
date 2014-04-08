
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.developer.DeveloperCreatePage;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;

import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import org.testng.annotations.Test;

/**
 *
 * @author Tatiana
 */

@UseWebDriver
@QCId("3569")
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
