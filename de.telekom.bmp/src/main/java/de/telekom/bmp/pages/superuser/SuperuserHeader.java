package de.telekom.bmp.pages.superuser;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel Biehl
 */
@Singleton
@Path("/superuser")
public class SuperuserHeader extends Page {

    @Inject
    public SuperuserHeader(BmpApplication app) {
        super(app);
    }

    public static class Tabs extends Container {

        public Tabs(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = ".//a[contains(@href, 'dashboard')]")
        public Link dashboard;
        @FindBy(xpath = ".//a[contains(@href, 'exceptions')]")
        public Link exceptions;
        @FindBy(xpath = ".//a[contains(@href, 'billingAdmin')]")
        public Link billingAdmin;
        @FindBy(xpath = ".//a[contains(@href, 'bills')]")
        public Link bills;
        @FindBy(xpath = ".//a[contains(@href, 'reportProcess')]")
        public Link reports;
        @FindBy(xpath = ".//a[contains(@href, 'rebuildindex')]")
        public Link rebuildindex;
        @FindBy(xpath = ".//a[contains(@href, 'marketplaces')]")
        public Link marketplaceSettings;
    }

    @FindBy(id = "subnav-header")
    public Tabs tabs;

}
