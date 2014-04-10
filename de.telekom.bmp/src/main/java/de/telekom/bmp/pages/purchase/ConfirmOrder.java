package de.telekom.bmp.pages.purchase;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */
@Singleton

@Path("/payment")
public class ConfirmOrder extends Page {

    @Inject
    public ConfirmOrder(BmpApplication app) {
        super(app);
    }
    
    
    @FindBy(xpath = "//button[@id='placeOrder']")
    public TextField placeOrderBtn;
    
    @FindBy (xpath = ".//a[@class= 'back']")
    public Link goBackLnk;
    
    @FindBy (xpath = ".//div[contains(@class, 'invoice-item last')]//div[@class='invoice-value']")
    public Text BruttoValue;
    
    @FindBy (xpath = ".//div[contains(@class, 'invoice-item recurring')]//span[@class='invoice-value']")
    public Text RecurringValue;
    
}
