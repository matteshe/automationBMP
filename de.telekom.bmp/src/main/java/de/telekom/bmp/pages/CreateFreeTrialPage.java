package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Link;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("/apps/2025")
public class CreateFreeTrialPage extends Page {

    @Inject
    public CreateFreeTrialPage(BmpApplication app) {
        super(app);
    }

    @FindBy(xpath = "//*[@id='2025']/td[2]/h2/a")
    public Link createFreeTrialAsdfLnk;
    
    @FindBy(xpath = "//*[@id='overviewLi']/a")
    public Link overviewLnk;
    
    @FindBy(xpath = "//*[@id='reviewsLi']/a")
    public Link reviewsLnk;
    
    @FindBy(xpath = "//*[@id='questionsLi']/a")
    public Link questionsLnk;
    
    @FindBy(xpath = "//*[@id='editionsLi']/a")
    public Link pricingLnk;
    
    @FindBy(xpath = "//div[@class='pricing-buttons']/div/button")
    public Button try3DaysBtn;
    
    @FindBy(xpath = "//button[@class='continue buttonResponse continue-to-next']")
    public Button continueBtn;
    
    @FindBy(xpath = "//div[@class='buy-button']/button")
    public Button startFreeTrialBtn;
    
    @FindBy(xpath = "//*[@id='placeOrder']")
    public Button placeOrderBtn;
    
    @FindBy(xpath = "//*[@id='return']")
    public Button goToMyAppsBtn;
}
