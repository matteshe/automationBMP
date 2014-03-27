package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
public class MosiPage extends Page {

    @Inject
    public MosiPage(BmpApplication app) {
        super(app);
    }

    @Override
    public String getPath() {
        return "/mosi/admin";
    }
    
    @FindBy(xpath = "//*[@id='main']/div/div[1]/button")
    public Button pingMOSIBtn;
    
    @FindBy(xpath = "//*[@id='main']/div/div[2]/ul/li/span")
    public TextField pingMOSISuccessfullTxt; 
    
    
}
