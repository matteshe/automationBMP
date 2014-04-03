package de.telekom.bmp.pages.channel;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Option;
import de.telekom.testframework.selenium.controls.TextField;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Nomop
 */
@Singleton
@Path("/channel/products")
public class DiscountsPage extends Page {

    @Inject
    public DiscountsPage(BmpApplication app) {
        super(app);
    }

    @FindBy(xpath = "//*[@id='addDiscountCode']")
    public Button rabattCodeHinzBtn;

    @FindBy(xpath = "//*[@id='codeApply-w-lbl']")
    public Option codeOpt;

    @FindBy(name = "discountTypeRadioGroup:codeDiscount:codeBorder:codeBorder_body:code")
    public TextField codeInput;

    @FindBy(name = "discountTypeRadioGroup")
    public Option multipleCodeOpt;

    @FindBy(xpath = "//div[@class='form-field']//input[contains(@value, 'radio325')]")
    public Option procentPriceOpt;

    @FindBy(xpath = "//div[@class='form-field']//input[contains(@value, 'radio326')]")
    public Option fixPriceOpt;
}
