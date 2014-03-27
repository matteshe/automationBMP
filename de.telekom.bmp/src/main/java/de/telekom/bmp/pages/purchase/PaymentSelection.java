package de.telekom.bmp.pages.purchase;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Control;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Tatiana
 */
@Singleton
@Path("")
public class PaymentSelection extends Page {

    @Inject
    public PaymentSelection(BmpApplication app) {
        super(app);
    }

    @UseParent
    public static class PaymentMethod extends Control {

        public PaymentMethod(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }
        @FindBy(tagName = "p")
        public Text name;

        @FindBy(xpath = "//div[@id='payment']")
        public Container parent;
    }

    @FindBy(xpath = "//div[@id='payment']//input[@value = '%s']")
    public Parameterized<PaymentMethod> paymentRadioBtn;

    @FindBy(xpath = "//div[@id='submitbtn']/a")
    public Button nextBtn;

//    @FindBy(xpath = "//div[@id='cancelbtn']")
    @FindBy(xpath = "//div[@id='cancelbtn']/a")
    public Button cancelBtn;

    public Control paymentRadioSelect(String method) {

        int value = 0;
        switch (method) {
            case "Telekom Festnetzrechnung":
                value = 0;
                break;
            case "Kreditkarte":
                value = 1;
                break;
            case "ClickandBuy":
                value = 2;
                break;
            case "PayPal":
                value = 3;
                break;
            case "Bezahlen per Lastschrift":
                value = 4;
                break;
        }
        return paymentRadioBtn.get(value);
    }
}
