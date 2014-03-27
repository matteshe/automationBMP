package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("/purchase")
public class PurchasePage extends Page {

    @Inject
    public PurchasePage(BmpApplication app) {
        super(app);
    }

    // Not necessary
//    @Override
//    public String getPath() {
//        return "/purchase";
//    }
    
    @FindBy(name = "editionGroup:appEditionsContainer:appEditions:0:subPricingRow:unitsColumns:0:unitsInput:enterUnits")
    public TextField numberOfUserTxt;
    
    @FindBy(xpath = "//*[@id='reviewsLi']/a")
    public Link reviewsLnk;
    
    @FindBy(xpath = "//*[@id='questionsLi']/a")
    public Link questionsLnk;
    
    @FindBy(xpath = "//*[@id='editionsLi']/a")
    public Link pricingLnk;
    
    @FindBy(xpath = "//div[@class='buttons']/button[@class='continue buttonResponse continue-to-next mosiRec']")
    public Button continueBtn;
    
    
    
    
}
