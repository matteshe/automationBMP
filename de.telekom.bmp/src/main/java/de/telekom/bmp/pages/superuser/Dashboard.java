package de.telekom.bmp.pages.superuser;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Control;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Row;
import de.telekom.testframework.selenium.controls.Table;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.util.List;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel Biehl
 */
@Singleton
@Path("/superuser/dashboard")
public class Dashboard extends Page {

    @Inject
    public Dashboard(BmpApplication app) {
        super(app);
    }

    public static class Tabs extends Container {

        public Tabs(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = ".//a[contains(@href, 'companies')]")
        public Link companies;
        @FindBy(xpath = ".//a[contains(@href, 'users')]")
        public Link users;
        @FindBy(xpath = ".//a[contains(@href, 'applications')]")
        public Link applications;
    }

    @FindBy(className = "account-tabs")
    public Tabs tabs;

    public static class Companies extends Container {

        public Companies(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = ".//button[contains(@class, 'tableToggle') and not(contains(@style, 'display: none'))]")
        public Button showFilters;

        public static class TheTable extends Table {

            public TheTable(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
                super(driver, locator, webElement);
            }

            public static class TheRow extends Row {

                public TheRow(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
                    super(driver, locator, webElement);
                }

                @FindBy(xpath = "./td[1]/span")
                public Control status;

                @FindBy(xpath = "./td[2]//a")
                public Link name;
            }

            @FindBy(xpath = "./tbody/tr")
            public List<TheRow> rows;

            @FindBy(xpath = "./tbody/tr/td[2]/a/span[text()='%s']/../../..")
            public Parameterized<TheTable.TheRow> rowByName;
        }

        @FindBy(xpath = ".//table[@class='item-table']")
        public TheTable table;

    }

    @FindBy(xpath = ".//div[@class='account-tab']")
    public Companies companies;
}
