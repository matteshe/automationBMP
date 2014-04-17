package de.telekom.testframework.selenium;

import de.telekom.testframework.selenium.internal.FieldSearchContextGetter;
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
public abstract class Application implements SearchContext, FieldSearchContextGetter, WebDriverWrapper {

    public Application(WebDriver webDriver) {
        assert webDriver != null : "webDriver is null";

        this.webDriver = webDriver;
    }

    final WebDriver webDriver;

    /**
     *
     * @return
     */
    @Override
    public WebDriver getWebDriver() {
        return webDriver;
    }

    void handle(String action, Runnable r, Object... args) {
        ActionHelper.handle(this, this.getClass().getSimpleName(), action, r, args);
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
        handle("navigate to", new Runnable() {

            @Override
            public void run() {

                getWebDriver().navigate().to(u);

                ActionHelper.waitUntilPageLoaded(webDriver, this);

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
        return WebElementContainer.find(clz, this, webDriver, by);
    }

    public List<WebElement> findAll(By by) {
        return findAll(WebElement.class, by);
    }

    public <T extends WebElement> List<T> findAll(Class<T> clz, By by) {
        return WebElementContainer.findAll(clz, this, webDriver, by);
    }

}
