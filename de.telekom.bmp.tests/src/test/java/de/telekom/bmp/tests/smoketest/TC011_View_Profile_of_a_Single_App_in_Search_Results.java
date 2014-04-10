package de.telekom.bmp.tests.smoketest;

import com.google.inject.Inject;
import de.telekom.bmp.BmpApplication;
import de.telekom.bmp.pages.FindApplicationsPage;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.Home;
import de.telekom.bmp.pages.VoucherTestApp001002003Page;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import static de.telekom.testframework.selenium.Matchers.isCurrentPage;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UseWebDriver
//@Test(groups = {"qcid-5506"})
@QCId("5503")
public class TC011_View_Profile_of_a_Single_App_in_Search_Results {

    @Inject
    BmpApplication app;

    @Inject
    Home home;

    @Inject
    Header header;

    @Inject
    VoucherTestApp001002003Page voucherTestPage;

    @Inject
    FindApplicationsPage findAppsPage;

    @BeforeMethod
    public void setup() {
        
        home.navigateTo();

        // sets the englisch language
//        if (header.languageToogleEN.isDisplayed()) {
//            click(header.languageToogleEN);
//        }

    }

    @Test
    public void test_011_View_Profile_of_a_Single_App_in_Search_Results() {

        //TODO configure search Text. Properties File?
        set(header.searchInput,"voucher app");
        click(header.searchBtn);

        
        click(findAppsPage.viewProfileVoucherApp);
        assertThat(voucherTestPage, isCurrentPage());
        
        click(voucherTestPage.overviewLnk);
        click(voucherTestPage.reviewsLnk);
        click(voucherTestPage.questionsLnk);
        click(voucherTestPage.pricingLnk);       

    }

}
