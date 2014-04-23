package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@UseParent
public class Mosi extends Page {

    @Inject
    public Mosi(BmpApplication app) {
        super(app);
    }

    @Override
    public String getPath() {
        return "/mosi/admin";
    }

    @FindBy(xpath = "//div[@id='main']")
    Container parent;

    @FindBy(xpath = ".//button[contains(@onclick, 'LinkListener-ping')]")
    public Button pingMOSI;

    @FindBy(className = "feedbackPanel")
    public FeedbackPanel feedbackPanel;
}
