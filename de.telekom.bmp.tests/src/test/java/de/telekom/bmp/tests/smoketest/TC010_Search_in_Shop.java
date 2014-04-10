package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.pages.FindApplicationsPage;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Assert.assertThat;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UseWebDriver
//@Test(groups = {"qcid-5506"})
@QCId("5502")
public class TC010_Search_in_Shop {

    @Inject
    BmpApplication app;

    @Inject
    Home home;

    @Inject
    Header header;

    @Inject
    FindApplicationsPage findAppsPage;

    @BeforeMethod
    public void setup() {
        
        navigateTo(home);

    }

    @Test
    public void test_010_Search_in_Shop() {

        //TODO configure search Text. Properties File?
        header.searchInput.set("autom");
        header.searchBtn.click();

        assertThat(findAppsPage,isCurrentPage());
        
    }

}
