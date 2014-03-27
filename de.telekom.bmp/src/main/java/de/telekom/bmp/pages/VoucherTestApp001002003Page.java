package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Link;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
public class VoucherTestApp001002003Page extends Page {

    @Inject
    public VoucherTestApp001002003Page(BmpApplication app) {
        super(app);
    }

    @Override
    public String getPath() {
        return "/apps/2114";
    }
    
    @FindBy(xpath = "//*[@id='overviewLi']/a")
    public Link overviewLnk;
    
    @FindBy(xpath = "//*[@id='reviewsLi']/a")
    public Link reviewsLnk;
    
    @FindBy(xpath = "//*[@id='questionsLi']/a")
    public Link questionsLnk;
    
    @FindBy(xpath = "//*[@id='editionsLi']/a")
    public Link pricingLnk;
    
    
}
