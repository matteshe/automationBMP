package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.ActionHelper;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Form;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.TextField;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@UseParent
public class Header extends Page {

    @Inject
    public Header(BmpApplication app) {
        super(app);
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FindBy(xpath = "//div[@id='header']|//header")
    public Container parent;

    public static class AccountLink extends Link {

        public AccountLink(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @Override
        public void click() {

            if (locator.searchContextGetter.getSearchContext() instanceof AccountMenu) {
                ((AccountMenu) locator.searchContextGetter.getSearchContext()).moveTo();
            }
            super.click();

            ActionHelper.waitUntilPageLoaded(webDriver, false);
        }
    }

    public static class AccountMenu extends Button {

        public AccountMenu(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = ".//a[@id='logout' or text()='Abmelden']")
        public AccountLink logout;

        @FindBy(xpath = ".//a[@id='myProfile' or text()='Mein Profil']")
        public AccountLink myProfile;
    }

    @FindBy(xpath = ".//div[@class='dropdown-parent' or @id='topbar-login']")
    public AccountMenu account;

    public static class SettingsLink extends Link {

        public SettingsLink(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @Override
        public void click() {

            if (locator.searchContextGetter.getSearchContext() instanceof SettingsMenu) {
                ((SettingsMenu) locator.searchContextGetter.getSearchContext()).moveTo();
            }
            super.click();

            ActionHelper.waitUntilPageLoaded(webDriver, false);
        }
    }

    public static class SettingsMenu extends Button {

        public SettingsMenu(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = ".//a[text()='Partner']")
        public SettingsLink developer;

        @FindBy(xpath = ".//a[text()='Channel']")
        public SettingsLink channelUser;

        @FindBy(xpath = ".//a[contains(@href,'dashboard')]")
        public SettingsLink superUser;

        @FindBy(id = "account")
        public SettingsLink account;
    }

    @FindBy(xpath = ".//li[contains(@class, 'settings-container')]")
    public SettingsMenu settings;

    @FindBy(xpath = ".//a[@id='myapps']")
    public Link myApps;

    @FindBy(xpath = ".//form[@name='searchForm' or @class='searchbox-form']")
    Form searchForm;

    @FindBy(xpath = ".//input[@name='q' or @id='qinput']")
    @UseParent("searchForm")
    public TextField searchInput;

    @FindBy(xpath = ".//input[@type='submit']|.//button[@type='submit']")
    @UseParent("searchForm")
    public Button searchBtn;

    @FindBy(xpath = "//a[contains(@href, './signup') or contains(text(),'Registrieren')]")
    public Button register;

    //only for cms homepage
    @FindBy(xpath = ".//a[contains(@href, './login')]")
    public Button login;

    @FindBy(xpath = "//a[contains(text(),'EN')]")
    public Link toogle_EN_LanguageLnk;

    @FindBy(xpath = "//a[contains(text(),'DE')]")
    public Link toogle_DE_LanguageLnk;
}
