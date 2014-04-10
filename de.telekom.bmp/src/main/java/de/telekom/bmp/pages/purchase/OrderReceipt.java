package de.telekom.bmp.pages.purchase;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Control;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */
@Singleton

@Path("/orderreceipt")
public class OrderReceipt extends Page {

    @Inject
    public OrderReceipt(BmpApplication app) {
        super(app);
    }
    
    
    public static class InfoRow extends Control {

        public InfoRow(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }
        @FindBy(xpath = "//div[@class='info-item']")
        public Text infoIds;

    }
    
       
    @FindBy (xpath = ".//button[@id= 'return']")
    public Button goToMyApps;
    
    @FindBy (xpath = ".//div[contains(@class, 'invoice-item last')]//div[@class='invoice-value']")
    public Text BruttoValue;
    
    @FindBy (xpath = ".//div[contains(@class, 'invoice-item recurring')]//div[@class='invoice-value']")
    public Text RecurringValue;
    
}
