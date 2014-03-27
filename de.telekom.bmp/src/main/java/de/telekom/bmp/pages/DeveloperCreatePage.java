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
public class DeveloperCreatePage extends Page {

    @Inject
    public DeveloperCreatePage(BmpApplication app) {
        super(app);
    }

    @Override
    public String getPath() {
        return "/cms/create";
    }
    
    @FindBy(xpath = "//*[@id='subnav-header']/div/ul/li/a")
    public Link produkteLnk;
    
    @FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[2]/a")
    public Link rechnungenLnk;
    
    @FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[3]/a")
    public Link integrationergnLnk;
    
    @FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[4]/a")
    public Link documentationLnk;
    
    @FindBy(xpath = "//*[@id='subnav-header']/div/ul/li[2]/a")
    public Link auszahlungenLnk;
    
    
}
