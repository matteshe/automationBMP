package de.telekom.bmp.pages;

import de.telekom.bmp.BmpApplication;
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

        }
    }

    public static class AccountMenu extends Button {

        public AccountMenu(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = ".//a[@id='logout' or text()='Abmelden']")
        public AccountLink logoutLnk;

        @FindBy(xpath = ".//a[@id='myProfile' or text()='Mein Profil']")
        public AccountLink myProfileLnk;
    }

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

        }
    }

    public static class SettingsMenu extends Button {

        public SettingsMenu(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @FindBy(xpath = "//a[text()='Partner')]")
        public SettingsLink developerLnk;

        @FindBy(xpath = "//a[text()='Channel')]")
        public SettingsLink channelUserLnk;

        @FindBy(xpath = "//a[text()='Superbenutzer')]")
        public SettingsLink superuserLnk;

        //@FindBy(xpath = ".//ul[@id='appdirectnav']//a[@id='account'] | .//li[@class='last']//a[@title = 'Unternehmen']")
        @FindBy(xpath = "//a[text()='Unternehmen']")
        public SettingsLink accountLnk;
    }

    //@FindBy(xpath = ".//li[@class='last']/a | .//li[@class='info-parent settings-container']")
    @FindBy(xpath = "//a[contains(@href, '/einstellungen/')] | .//li[@class='info-parent settings-container']")
    public SettingsMenu settingsMenu;

    @FindBy(xpath = ".//a[@id='myapps']/span | .//div[@class='hlist right']//a")
    public AccountMenu launchPadLnk;

    @FindBy(xpath = ".//div[@class='dropdown-parent' or @id='topbar-login']")
    public AccountMenu accountMenu;

    @FindBy(xpath = ".//form[@name='searchForm' or @class='searchbox-form']")
    Form searchForm;

    @FindBy(xpath = ".//input[@name='q' or @id='qinput']")
    @UseParent("searchForm")
    public TextField searchInput;

    @FindBy(xpath = ".//input[@type='submit']|.//button[@type='submit']")
    @UseParent("searchForm")
    public Button searchBtn;

    //only for cms homepage
    @FindBy(xpath = ".//div[@id='topbar-login']|.//button")
    public Button loginBtn;

    @FindBy(xpath = "//a[contains(text(),'EN')]")
    public Link toogle_EN_LanguageLnk;

    @FindBy(xpath = "//a[contains(text(),'DE')]")
    public Link toogle_DE_LanguageLnk;

}
