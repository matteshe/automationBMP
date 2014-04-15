package de.telekom.testframework.selenium.tutorial.application.pages;

import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.CheckBox;
import de.telekom.testframework.selenium.controls.Control;
import de.telekom.testframework.selenium.controls.RadioButton;
import de.telekom.testframework.selenium.controls.TextField;
import de.telekom.testframework.selenium.tutorial.application.TutorialApplication;
import java.util.List;
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
    
    @FindBy(name= "stayLoggedIn")
    public CheckBox stayLoggedIn;

    @FindBy(name = "hiddenText")
    public TextField hiddenText;
    
    @FindBy(name = "disabledText")
    public TextField disabledText;

    @FindBy(name = "notExistingText")
    public TextField notExistingText;
    
    @FindBy(name = "notExistingText")
    public List<TextField> notExistingTexts;
    
    @FindBy(id = "abc")
    public RadioButton aradioAbc;

    @FindBy(id = "def")
    public RadioButton aradioDef;

    @FindBy(id = "ghi")
    public RadioButton aradioGhi;
    
    @FindBy(name = "delayedDisabledText")
    public TextField delayedDisabledText;
    
    @FindBy(name = "toggleDisableField")
    public Button toggleDisableField;
    
    @FindBy(name = "delayedToggleDisableField")
    public Button delayedToggleDisableField;

}
