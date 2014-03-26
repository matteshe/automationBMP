package de.telekom.testframework.selenium;

import de.telekom.testframework.selenium.internal.FieldElementLocator;
import de.telekom.testframework.selenium.internal.FieldElementLocator.SearchContextWrapper;
import de.telekom.testframework.selenium.internal.ListOfWebElementProxy;
import de.telekom.testframework.selenium.internal.WebElementProxy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel
 */
public abstract class Application implements SearchContext, SearchContextWrapper, WebDriverWrapper {

    public Application(WebDriver webDriver) {
        assert webDriver != null : "webDriver is null";

        this.webDriver = webDriver;
    }

    private final WebDriver webDriver;

    /**
     *
     * @return
     */
    @Override
    public WebDriver getWebDriver() {
        return webDriver;
    }

    void handle(String action, Runnable r, Object... args) {
        ActionHandler.handle(this, this.getClass().getSimpleName(), action, r, args);
    }

    public void quit() {
        handle("quit", new Runnable() {

            @Override
            public void run() {
                webDriver.quit();
            }
        });
    }

    public void close() {
        handle("close", new Runnable() {

            @Override
            public void run() {
                webDriver.close();
            }
        });

    }

    public abstract String getURLString();

    public void navigateTo(final String page) {

        URL url = buildUrl(page);

        if (url == null) {
            throw new RuntimeException("invalid url");
        }

        final URL u = url;
        handle("navigateTo", new Runnable() {

            @Override
            public void run() {

                getWebDriver().navigate().to(u);

                ActionHandler.waitForPageLoaded(webDriver, this);

            }
        }, url);

    }

    protected URL buildUrl(final String page) throws RuntimeException {
        URL url = null;

        try {
            url = new URL(getURLString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        if (page != null) {
            try {
                url = new URL(url, page);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return url;
    }

    public void navigateTo() {
        navigateTo(null);
    }

    @Override
    public List<WebElement> findElements(By by) {
        return findAll(WebElement.class, by);
    }

    @Override
    public WebElement findElement(By by) {
        return find(WebElement.class, by);
    }

    @Override
    public SearchContext getSearchContext() {
        return getWebDriver();
    }

    public WebElement find(By by) {
        return find(WebElement.class, by);
    }

    @SuppressWarnings("unchecked")
    public <T extends WebElement> T find(Class<T> clz, By by) {
        try {

            Constructor<?> constructor = WebElementContainer.getDelegatedElementConstructor(clz);

            FieldElementLocator locator = new FieldElementLocator(this, clz, by);
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

        FieldElementLocator locator = new FieldElementLocator(this, clz, by);
        List<T> list = ListOfWebElementProxy.createProxy(ClassLoader.getSystemClassLoader(), webDriver, constructor, locator);

        return list;
    }

}
