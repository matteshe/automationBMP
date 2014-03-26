package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Table;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

/**
 *
 * @author Daniel Biehl
 */
@Singleton
@Path("/listing")
public class Listing extends Page {

    @Inject
    public Listing(BmpApplication application) {
        super(application);
    }

    public static class AppInfo extends Container {

        public AppInfo(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(className = "appName")
        public Link appName;

        @FindBy(xpath = ".//button//*[text()='View Profile' or text()='Profil anzeigen']")
        public Button viewProfile;

        @FindBy(xpath = "./td[@class='appInfo']")
        Container appInfoColumn;
        
        @FindBy(xpath = "./td[@class='price']")
        Container priceColumn;

        @FindBys({
            @FindBy(className = "price"),
            @FindBy(tagName = "span")})
        @UseParent("priceColumn")
        public Text prize;
    }

    @FindBy(id = "listingTable")
    Container listingTable;

    @FindBy(tagName = "table")
    @UseParent("listingTable")
    public Table appInfoTable;

    @FindBy(xpath = ".//tr")
    @UseParent("appInfoTable")
    public List<AppInfo> appInfos;

    @FindBy(xpath = ".//tr//a[@class='appName' and text()='%s']/../../..")
    @UseParent("appInfoTable")
    public Parameterized<AppInfo> appInfo;

}
