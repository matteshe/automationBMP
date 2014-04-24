package de.telekom.bmp.pages.account;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.*;
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
@Path("/account")
public class AccountHeader extends Page {

    @Inject
    public AccountHeader(BmpApplication app) {
        super(app);
    }

    public static class Tabs extends Container {

        public Tabs(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = ".//a[contains(@href, 'home')]")
        public Link dashboard;
        @FindBy(xpath = ".//a[contains(@href, 'users')]")
        public Link users;
        @FindBy(xpath = ".//a[contains(@href, 'apps')]")
        public Link applications;
        @FindBy(xpath = ".//a[contains(@href, 'apps')]")
        public Link assignApplications;
        @FindBy(xpath = ".//a[contains(@href, 'bills')]")
        public Link billing;
        @FindBy(xpath = ".//a[contains(@href, './companySettings')]")
        public Link companySettings;
    }

    @FindBy(id = "subnav-header")
    public Tabs tabs;

}
