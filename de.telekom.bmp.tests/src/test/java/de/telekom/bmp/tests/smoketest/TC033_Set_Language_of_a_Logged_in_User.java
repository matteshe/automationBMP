package de.telekom.bmp.tests.smoketest;


import de.telekom.bmp.pages.*;
import static de.telekom.testframework.Actions.click;

import static de.telekom.testframework.Actions.navigateTo;
import static de.telekom.testframework.Assert.verifyThat;
import static de.telekom.testframework.Assert.waitUntil;
import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import static de.telekom.testframework.selenium.Matchers.loaded;
import static de.telekom.testframework.selenium.Matchers.text;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import javax.inject.Inject;
import static org.hamcrest.Matchers.is;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@QCId(value = "5594", state = QCState.Ongoing)
@UseWebDriver
public class TC033_Set_Language_of_a_Logged_in_User {

    @Inject
    Login login;
   
    @Inject
    Header header;

    @Test
    public void theTest() throws InterruptedException {
        navigateTo(login);
        waitUntil(login, is(loaded()));
        
        System.out.println(header.currentLanguage.getText());
        
        System.out.println(header.toggleLanguage.getText());
        System.out.println(header.currentLanguage.getText());
        verifyThat(header.toggleLanguage, text(is("asd")));
        click(header.toggleLanguage);
        
        waitUntil(login, is(loaded()));
    }

}
