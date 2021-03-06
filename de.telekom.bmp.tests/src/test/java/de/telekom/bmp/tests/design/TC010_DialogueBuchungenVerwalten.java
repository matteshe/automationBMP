
package de.telekom.bmp.tests.design;

import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.account.Bills;

import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.reporting.Reporter.reportMessage;

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
@QCId("3798")
public class TC010_DialogueBuchungenVerwalten {
    
    @Inject
    FunctionalActions functional;
            
    @Inject
    Bills bills;
        
    @Test
    public void test1() throws InterruptedException {
        
       functional.login("sysadmin4.0@test.com", "password");
       
       navigateTo(bills);
       
       click(bills.showFiltersBtn);
       int dropdownOptions = 0;
       int checkboxOptions = 0;
       int timeOptions = 0;
       int sortingOptions = 0;
       
       for (WebElement dropdown: bills.dropdownFilter) {
           dropdownOptions++;
           reportMessage(dropdown.getText());
       }
       
       for (WebElement checkbox: bills.dropdownFilter) {
           checkboxOptions++;
           reportMessage(checkbox.getText());
       }
       
       for (WebElement time: bills.dropdownFilter) {
           timeOptions++;
           reportMessage(time.getText());
       }
       
       verifyThat(dropdownOptions, is(greaterThanOrEqualTo(1)));
       verifyThat(checkboxOptions, is(greaterThanOrEqualTo(1)));
       verifyThat(timeOptions, is(greaterThanOrEqualTo(1)));
       
       verifyThat(bills.searchInput.isDisplayed());
       verifyThat(bills.billsTable.isDisplayed());
       
       for (WebElement sorting: bills.sortingCriteria) {
           sortingOptions++;
           reportMessage(sorting.getText());
       }
       
       verifyThat(sortingOptions, is(4));
      
       functional.logout();
       
    }
}
