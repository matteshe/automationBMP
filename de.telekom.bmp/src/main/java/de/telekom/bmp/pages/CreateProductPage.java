package de.telekom.bmp.pages;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Link;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
public class CreateProductPage extends Page {

    @Inject
    public CreateProductPage(BmpApplication app) {
        super(app);
    }

    @Override
    public String getPath() {
        return "/cms/create";
    }
    
    @FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[2]/a")
    public Link billsTab;
    
}
