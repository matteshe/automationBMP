package de.telekom.testframework.selenium;

import de.telekom.testframework.selenium.ActionHandler.RunnableFunction;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import de.telekom.testframework.selenium.internal.FieldSearchContextGetter;
import de.telekom.testframework.selenium.internal.ListOfWebElementProxy;
import de.telekom.testframework.selenium.internal.WebElementProxy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Daniel
 */
@Singleton
public class Browser implements WebDriverWrapper, FieldSearchContextGetter {

    @Inject
    protected static Browser instance = null;

    public static Browser getInstance() {
        return instance;
    }

    private final WebDriver webDriver;

    @Inject
    public Browser(WebDriver driver) {
        this.webDriver = driver;

        //setThisInstance();
    }

    void handle(String action, final Runnable r, Object... args) {
        handle(action, new RunnableFunction<Object>() {

            @Override
            public Object run() {
                r.run();
                return null;
            }
        }, args);
    }

    <T> T handle(String action, final RunnableFunction<T> r, Object... args) {
        return ActionHandler.handle(this, null, action, r, args);
    }

    @Override
    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    @Override
    public SearchContext getSearchContext() {
        return this.webDriver;
    }

    public WebElement find(By by) {
        return find(WebElement.class, by);
    }

    @SuppressWarnings("unchecked")
    public <T extends WebElement> T find(Class<T> clz, By by) {
        return WebElementContainer.find(clz, this, webDriver, by);
    }

    public List<WebElement> findAll(By by) {
        return findAll(WebElement.class, by);
    }

    public <T extends WebElement> List<T> findAll(Class<T> clz, By by) {
        return WebElementContainer.findAll(clz, this, webDriver, by);
    }

    /**
     * Opens a new tab in a browser
     *
     * @return the handle of the new tab
     */
    public String newTab() {

        return handle("new tab", new RunnableFunction<String>() {

            @Override
            public String run() {

                // we create a new window here, because selenium2 has no support for tabs
                // but maybe later they implement tabs handling
                new Actions(getWebDriver()).keyDown(Keys.CONTROL).sendKeys("n").keyUp(Keys.CONTROL).build().perform();

                String result = null;

                for (String s : webDriver.getWindowHandles()) {
                    result = s;
                }

                webDriver.switchTo().window(result);

                return result;
            }
        });
    }

    /**
     * Opens a new window in a browser
     *
     * @return the handle of the new tab
     */
    public String newWindow() {

        return handle("new window", new RunnableFunction<String>() {

            @Override
            public String run() {

                //new Actions(getWebDriver()).keyDown(Keys.CONTROL).sendKeys(Keys.chord("n")).keyUp(Keys.CONTROL).build().perform();
                new Actions(getWebDriver()).sendKeys(Keys.chord(Keys.CONTROL, "n")).build().perform();

                String result = null;

                for (String s : webDriver.getWindowHandles()) {
                    result = s;
                }

                webDriver.switchTo().window(result);

                return result;
            }
        });
    }

    /**
     * Closes the current browser window and activate the next opened window.
     * Does not close the main browser window.
     *
     * @return returns the activated browser windows handle
     */
    public String closeWindow() {
        return handle("close window", new RunnableFunction<String>() {

            @Override
            public String run() {
                // don't close the last window

                Set<String> ws = webDriver.getWindowHandles();
                if (ws.size() == 1) {
                    throw new CantCloseWindowException("can't close the last browser window");
                }

                webDriver.close();

                ws = webDriver.getWindowHandles();
                String result = null;

                for (String s : ws) {
                    result = s;
                }
                if (result != null) {
                    switchTo().window(ws.iterator().next());
                }

                return result;
            }
        });
    }

    public TargetLocator switchTo() {
        return new TargetLocator() {

            @Override
            public WebDriver frame(int index) {
                return webDriver.switchTo().frame(index);
            }

            @Override
            public WebDriver frame(String nameOrId) {
                return webDriver.switchTo().frame(nameOrId);
            }

            @Override
            public WebDriver frame(WebElement frameElement) {
                return webDriver.switchTo().frame(frameElement);
            }

            // FEATURE NOT IN Selenium Version less than 2.41.0. Before not fully tested we use 2.40.0!!!
//            @Override
//            public WebDriver parentFrame() {
//                return webDriver.switchTo().parentFrame();
//            }
            @Override
            public WebDriver window(final String nameOrHandle) {
                return handle("switch to window", new RunnableFunction<WebDriver>() {

                    @Override
                    public WebDriver run() {
                        return webDriver.switchTo().window(nameOrHandle);
                    }

                }, nameOrHandle);
            }

            @Override
            public WebDriver defaultContent() {
                return webDriver.switchTo().defaultContent();
            }

            @Override
            public WebElement activeElement() {
                return webDriver.switchTo().activeElement();
            }

            @Override
            public Alert alert() {
                return handle("switch to alert", new RunnableFunction<Alert>() {

                    @Override
                    public Alert run() {
                        return webDriver.switchTo().alert();
                    }
                });
            }
        };
    }

    public Navigation navigate() {
        return new Navigation() {

            @Override
            public void back() {
                handle("navigate back", new Runnable() {

                    @Override
                    public void run() {
                        webDriver.navigate().back();

                        ActionHandler.waitUntilPageLoaded(webDriver, this);
                    }
                });
            }

            @Override
            public void forward() {
                handle("navigate forward", new Runnable() {

                    @Override
                    public void run() {
                        webDriver.navigate().forward();
                        ActionHandler.waitUntilPageLoaded(webDriver, this);
                    }
                });
            }

            @Override
            public void to(final String url) {
                handle("navigate to", new Runnable() {

                    @Override
                    public void run() {
                        webDriver.navigate().to(url);
                        ActionHandler.waitUntilPageLoaded(webDriver, this);
                    }
                }, url);
            }

            @Override
            public void to(final URL url) {
                handle("navigate to", new Runnable() {

                    @Override
                    public void run() {
                        webDriver.navigate().to(url);
                        ActionHandler.waitUntilPageLoaded(webDriver, this);
                    }
                }, url);
            }

            @Override
            public void refresh() {
                handle("refresh", new Runnable() {

                    @Override
                    public void run() {
                        webDriver.navigate().refresh();
                        ActionHandler.waitUntilPageLoaded(webDriver, this);
                    }
                });
            }
        };
    }

    public Set<String> getWindowHandles() {
        return webDriver.getWindowHandles();
    }

    public String getCurrentWindowHandle() {
        return webDriver.getWindowHandle();
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public String getCurrentTitle() {
        return webDriver.getTitle();
    }

}
