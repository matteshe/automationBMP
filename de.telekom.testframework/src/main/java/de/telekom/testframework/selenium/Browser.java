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
public class Browser implements WebDriverWrapper, FieldSearchContextGetter {

    private final WebDriver webDriver;

    @Inject
    public Browser(WebDriver driver) {
        this.webDriver = driver;
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
        try {

            Constructor<?> constructor = WebElementContainer.getDelegatedElementConstructor(clz);

            FieldElementLocator locator = new FieldElementLocator(this, by);
            WebElement element = WebElementProxy.createProxy(ClassLoader.getSystemClassLoader(), webDriver, locator);

            return (T) constructor.newInstance(webDriver, locator, element);

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("can't instanciate class", e);
        }
    }

    public List<WebElement> findAll(By by) {
        return findAll(WebElement.class, by);
    }

    public <T extends WebElement> List<T> findAll(Class<T> clz, By by) {
        Constructor<?> constructor = WebElementContainer.getDelegatedElementConstructor(clz);

        FieldElementLocator locator = new FieldElementLocator(this, by);
        List<T> list = ListOfWebElementProxy.createProxy(ClassLoader.getSystemClassLoader(), webDriver, constructor, locator);

        return list;
    }

    public String newTab() {
        final String result = webDriver.getWindowHandle();
        handle("new tab", new Runnable() {

            @Override
            public void run() {

                // we create a new window here, because selenium2 has no support for tabs
                new Actions(getWebDriver()).sendKeys(Keys.CONTROL, "n").perform();

                String last = result;

                for (String s : webDriver.getWindowHandles()) {
                    last = s;
                }
                webDriver.switchTo().window(last);

            }
        });
        return result;
    }

    public String newWindow() {
        final String result = webDriver.getWindowHandle();
        handle("new window", new Runnable() {

            @Override
            public void run() {

                new Actions(getWebDriver()).sendKeys(Keys.CONTROL, "n").perform();

                String last = result;

                for (String s : webDriver.getWindowHandles()) {
                    last = s;
                }
                webDriver.switchTo().window(last);
            }
        });
        return result;
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

            @Override
            public WebDriver parentFrame() {
                return webDriver.switchTo().parentFrame();
            }

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
