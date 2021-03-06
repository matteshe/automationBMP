package de.telekom.bmp.pages.superuser.old;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Container;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Option;
import de.telekom.testframework.selenium.controls.TextField;
import java.util.List;
import javax.inject.Singleton;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author Daniel
 */
@Singleton
@Path("/superuser/dashboard")
@Deprecated
public class DashboardOld extends Page {

    @Inject
    public DashboardOld(BmpApplication app) {
        super(app);
    }

    @FindBy(xpath = "//table[@class='item-table']")
    private Container itemTable;

    @FindBy(xpath = "//a")
    @UseParent("itemTable")
    public List<Link> userLinks;

    @FindBy(xpath = "//ul[@class='account-tabs']//a[text()='Benutzer']")
    public Link userLnk;

    @FindBy(xpath = "//ul[@class='account-tabs']//a[text()='Unternehmen']")
    public Link companyLnk;

    @FindBy(xpath = "//ul[@class='account-tabs']//a[text()='Applikationen']")
    public Link applicationLnk;

    @FindBy(xpath = "//div[@class='smallSearch']//input")
    public TextField smallSearchInput;

    @FindBy(xpath = "//input[contains(@name, 'companyTable') and contains(@name, 'channelAdmin')]")
    public Option channelAdminChkBox;

    @FindBy(xpath = "//input[contains(@name, 'userTable') and contains(@name, 'channelAdmin')]")
    public Option userChannelAdminChkBox;

    @FindBy(xpath = "//input[contains(@name, 'userTable') and contains(@name, 'channelSupport')]")
    public Option userChannelSupportChkBox;

    @FindBy(xpath = "//input[contains(@name, 'userTable') and contains(@name, 'superuser')]")
    public Option superUserChkBox;

    @FindBy(xpath = "//button//span[text()='Übernehmen']")
    public Button submit;
}
