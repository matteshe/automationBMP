package de.telekom.bmp.pages.playground;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.TextField;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */
@Singleton

@Path("/purchase/1262")
public class CreateOrder11 extends Page {

    @Inject
    public CreateOrder11(BmpApplication app) {
        super(app);
    }
    
//    @FindBy(xpath = "//form[@class='createOrderForm']")
//    public Container parent;
 
    @FindBy(xpath= "//div[@class='editionRow']")
    public List<Button> editions;
    
//    @FindBy(xpath=" //div[@class='editionRow']//span/label[contains('%s')]")
//    @FindBy(xpath="//ul[@class='csc-menu csc-menu-19']//a[contains(@title, '%s')]")
    @FindBy(xpath=" //div[@class='editionRow']//a[contains(@data-json, '%s')]")
    public Parameterized<Button> edition;
    
    @FindBy (name = "discountCodeContainer:discountCode")
    public TextField discountCode;
    
    
    
    @FindBy (xpath = ".//div[@class='discountForm']//button")
    public Button applyDiscount;
    
    
    @FindBy (xpath = ".//button[contains(@class, 'continue-to-next')]")
    public Button nextStep;
    
}
