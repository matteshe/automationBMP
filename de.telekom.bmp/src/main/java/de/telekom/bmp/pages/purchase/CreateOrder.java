package de.telekom.bmp.pages.purchase;
            
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
 * @author Tatiana
 */
@Singleton

@Path("/purchase")
public class CreateOrder extends Page {

    @Inject
    public CreateOrder(BmpApplication app) {
        super(app);
    }
    
    @FindBy(xpath= "//form[contains(@class, 'createOrderForm')]")
    public Form createOrderForm;
    
    public static class EditionRow extends Control {

        public EditionRow(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }
        @FindBy(tagName = "label")
        public Text name;

    }
    

    @FindBy(xpath= "//div[contains(@class, 'editionRow')]")
    public List<EditionRow> editions;
      
    @FindBy(xpath= "//div[contains(@class, 'editionRow')]//label[text()='%s']//../..")
    public Parameterized<EditionRow> edition;
     
    @FindBy (name = "discountCodeContainer:discountCode")
    public TextField discountCode;
       
    @FindBy (xpath = ".//div[@class='discountForm']//button")
    public Button applyDiscount;
        
    @FindBy (xpath = ".//button[contains(@class, 'continue-to-next')]")
    public Button nextStep;
    
    @FindBy (xpath = ".//a[contains(@id, 'backButton')]")
    public Link goBack;

  
}
