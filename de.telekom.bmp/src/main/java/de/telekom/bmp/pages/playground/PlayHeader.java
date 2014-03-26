package de.telekom.bmp.pages.playground;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
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
 * @author Daniel
 */
@Singleton
@UseParent
public class PlayHeader extends Page {

    @Inject
    public PlayHeader(BmpApplication application) {
        super(application);
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException();
    }

    @FindBy(xpath = "//div[@id='header']|//header")
    public Container parent;

    public static class AccountLink extends Link {

        public AccountLink(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
            super(driver, locator, webElement);
        }

        @Override
        public void click() {

            if (locator.searchContext.getSearchContext() instanceof AccountMenu) {
                ((AccountMenu) locator.searchContext.getSearchContext()).moveTo();
            }
            super.click();
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
        @FindBy(xpath = ".//a[@id='myCompany' or text()='Mein Unternehmen']")
        public AccountLink myCompany;
        @FindBy(xpath = ".//a[@id='mySettings' or text()='Meine Einstellungen']")
        public AccountLink mySettings;
    }

    @FindBy(xpath = ".//div[@class='dropdown-parent' or @id='topbar-login']")
    public AccountMenu account;

}
