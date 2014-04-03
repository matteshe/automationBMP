package de.telekom.testframework.selenium.tutorial.application.pages;

import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.TextField;
import de.telekom.testframework.selenium.tutorial.application.TutorialApplication;
import javax.inject.Inject;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
public class Home extends Page {

    @Inject
    public Home(TutorialApplication application) {
        super(application);
    }

    @FindBy(name = "username")
    public TextField username;

    @FindBy(name = "password")
    public TextField password;

    @FindBy(name = "hiddenText")
    public TextField hiddenText;
    
    @FindBy(name = "disabledText")
    public TextField disabledText;

    @FindBy(name = "notExistingText")
    public TextField notExistingText;
    
}
