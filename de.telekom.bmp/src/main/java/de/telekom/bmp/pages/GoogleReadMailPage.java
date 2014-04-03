package de.telekom.bmp.pages;

import de.telekom.bmp.GoogleApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.TextField;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("")
public class GoogleReadMailPage extends Page {

    @Inject
    public GoogleReadMailPage(GoogleApplication app) {
        super(app);
    }
    
    @FindBy(xpath="//span[@class='ts']")
    public List<Link> emailLinks;

    @FindBy(xpath="//div[@class='msg']//a")
    public List<Link> linksInMessage; 
    
    @FindBy(xpath="//div[@class='msg']//a[contains(@href, '%s')]")
    public Parameterized<Link> confirmLink;
}
