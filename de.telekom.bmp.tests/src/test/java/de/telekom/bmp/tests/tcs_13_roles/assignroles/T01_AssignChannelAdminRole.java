/**
 *
 */
package de.telekom.bmp.tests.tcs_13_roles.assignroles;

import de.telekom.bmp.data.Datapool;
import de.telekom.bmp.data.User;
import de.telekom.bmp.data.UserRole;
import de.telekom.bmp.functional.FunctionalActions;
import de.telekom.bmp.pages.Header;
import de.telekom.bmp.pages.superuser.Dashboard;
import de.telekom.bmp.pages.superuser.SuperuserHeader;
import static de.telekom.testframework.Actions.*;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import static java.util.concurrent.TimeUnit.SECONDS;
import javax.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Mathias Herkt, Daniel Biehl
 *
 */
@UseWebDriver
@QCId(value = "5612", state = QCState.Ongoing)
public class T01_AssignChannelAdminRole {

    @Inject
    FunctionalActions fa;

    @Inject
    Datapool datapool;

    @Inject
    Header header;

    @Inject
    SuperuserHeader superuserHeader;

    @Inject
    Dashboard dashboard;

    User superuser;
    User user;

    @BeforeClass
    public void preparation() {
        superuser = datapool.helpers().getSuperUser();
        user = datapool.validUsers()
                .field(User.Fields.registered).equal(true)
                .field(User.Fields.role).notEqual(UserRole.CHANNELADMIN).get();

    }

    @AfterClass
    public void finalization() {
        if (user != null) {
            datapool.save(user);
        }
    }

    @Test
    public void theTest() {
        //user.valid = false;

        assertThat("we have a superuser", superuser, is(not(nullValue())));
        assertThat("we have a user", user, is(not(nullValue())));
        fa.login(superuser);

        click(header.settings.superUser);
        click(superuserHeader.tabs.dashboard);

        click(dashboard.tabs.companies);

        System.out.println(dashboard.companies.table.rowByName.get(user.company.name).name.getTextContent());

        fa.logout();
    }
}
