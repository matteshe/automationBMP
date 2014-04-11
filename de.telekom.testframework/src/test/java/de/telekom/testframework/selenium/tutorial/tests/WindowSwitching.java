package de.telekom.testframework.selenium.tutorial.tests;

import static de.telekom.testframework.Actions.*;

import de.telekom.testframework.selenium.CantCloseWindowException;
import de.telekom.testframework.selenium.annotations.ResetWebDriver;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.TextField;
import de.telekom.testframework.selenium.tutorial.application.TutorialApplication;
import static java.util.concurrent.TimeUnit.SECONDS;
import javax.inject.Inject;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@UseWebDriver
public class WindowSwitching {

    @Inject
    TutorialApplication application;
    
    @Test
    @ResetWebDriver
    public void switchWindows() {
        navigateTo(application);

        newWindow();

        navigateTo("http://www.heise.de");

    }

    @Test
    @ResetWebDriver
    public void switchWindows1() {
        String golem = getCurrentWindowHandle();

        navigateTo("http://www.golem.de");
        
        String heise = newWindow();
        navigateTo("http://www.heise.de");

        String google = newWindow();
        navigateTo("http://www.google.com");

        switchTo(window(heise));

        closeWindow();

        switchTo(window(golem));        
        click(find(By.xpath("//*[@class='cluster-header']/a")));
              
        switchTo(window(google));
        set(find(TextField.class, By.name("q")), "abc");
        click(find(Button.class, By.name("btnG")));

        closeWindow();
    }
    
    @Test
    @ResetWebDriver
    public void switchWindows2() {
        newWindow();
        newWindow();
        newWindow();
        closeWindow();
        closeWindow();
        closeWindow();
       
        waitFor(5, SECONDS);
        newWindow();
        waitFor(5, SECONDS);        
    }
    
    @Test(expectedExceptions = CantCloseWindowException.class)
    @ResetWebDriver
    public void cantCloseWindowCheck() {
        newWindow();
        newWindow();
        newWindow();
        closeWindow();
        closeWindow();
        closeWindow();
       
        closeWindow();
    }
}
