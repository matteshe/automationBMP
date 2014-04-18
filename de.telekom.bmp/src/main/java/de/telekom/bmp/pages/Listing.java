package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Column;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Row;
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

    public static class ListingTable extends Table {

        public static class ListingRow extends Row {

            public ListingRow(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
                super(driver, locator, webElement);
            }

            @FindBy(xpath = "./td[@class='appInfo']")
            Column appInfoColumn;

            @FindBy(className = "appName")
            @UseParent("appInfoColumn")
            public Link appName;

            @FindBy(xpath = "./td[@class='price']")
            Column priceColumn;

            @FindBys({
                @FindBy(className = "price"),
                @FindBy(tagName = "span")})
            @UseParent("priceColumn")
            public Text prize;

            @FindBy(xpath = ".//button//*[text()='View Profile' or text()='Profil anzeigen']")
            public Button viewProfile;
        }

        public ListingTable(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = "./tbody/tr")
        public List<ListingRow> rows;

        @FindBy(xpath = "./tbody/tr//a[@class='appName' and contains(text(), '%s')]/../../..")
        public Parameterized<ListingRow> rowByName;
    }

    @FindBys({
        @FindBy(id = "listingTable"),
        @FindBy(tagName = "table")})
    public ListingTable results;

    @Deprecated
    @FindBy(xpath = ".//tr")
    @UseParent("results")
    public List<ListingTable.ListingRow> appInfos;

    @Deprecated
    @FindBy(xpath = ".//tr//a[@class='appName' and text()='%s']/../../..")
    @UseParent("results")
    public Parameterized<ListingTable.ListingRow> appInfo;

}
