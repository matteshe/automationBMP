package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel, Tatiana
 */

@Singleton
@Path("/myapps")
public class MyApps extends Page {

    @Inject
    public MyApps(BmpApplication app) {
        super(app);
    } 
    
    @FindBy(xpath = ".//div[@id='myAppsWelcome']")
    public Link welcomeTeaser;
    
    @FindBy(xpath= "//div[contains(@class, 'myAppsPage')]//h1")
    public Text appsTitle;
    
    @FindBy(xpath = ".//div[@class='myAppsGrid']")
    Form appsGrid;
    
    @FindBy(xpath = ".//li[contains(@class, 'appsTour')]")
    @UseParent("appsGrid")
    public Link configApp;
    
    @FindBy(xpath = ".//li[@class = 'app']")
    @UseParent("appsGrid")
    public Link oneApp;
    
    @FindBy(xpath = "//div[@class = 'icon help']")
    public Button helpBtn;
    
    @FindBy(xpath = "//div[@class = 'helpText']/span")
    public Text helpText;
    
    @FindBy(xpath = "//div[@class = 'icon remove']")
    public Button closeHelpBtn;
    
    
}
