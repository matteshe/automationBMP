package de.telekom.bmp.pages;

import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
public class FeedbackPanel extends Container {

    public FeedbackPanel(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    @FindBy(xpath = ".//span[@class='feedbackPanelINFO']")
    public Text info;

    @FindBy(xpath = ".//span[@class='feedbackPanelERROR']")
    public Text error;
}
