
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.developer.DeveloperCreatePage;
import de.telekom.bmp.pages.account.Bills;
import de.telekom.bmp.pages.account.CompanySettings;
import de.telekom.bmp.pages.developer.Products;

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
public class TC015_OverviewDeveloper2 {
    
    @Inject
    FunctionalActions functional;
            
    @Inject
    Products devProducts;
    
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(devProducts);
       verifyThat(devProducts.isCurrentPage());
       
       verifyThat(devProducts.header.isDisplayed());
       verifyThat(devProducts.addProduct.isDisplayed());
       verifyThat(devProducts.productSummary.isDisplayed());
       verifyThat(devProducts.showFiltersBtn.isDisplayed());
       verifyThat(devProducts.searchInput.isDisplayed());
       verifyThat(devProducts.productTable.isDisplayed());
       
    }
}
