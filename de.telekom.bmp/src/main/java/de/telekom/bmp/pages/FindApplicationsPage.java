package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
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
public class FindApplicationsPage extends Page {

    @Inject
    public FindApplicationsPage(BmpApplication app) {
        super(app);
    }

    @Override
    public String getPath() {
        return "/listing";
    }
    
    @FindBy(xpath = "//*[@id='2114']/td[3]/div[1]/div/a/button")
    public Button viewProfileVoucherApp;
    
    @FindBy(xpath = "//*[@id='722']/td[2]/h2/a")
    public Link exchangeProduktLnk;
    
}
