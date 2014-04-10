package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.ActionHandler;
import de.telekom.testframework.selenium.SeleniumConfiguration;
import de.telekom.testframework.selenium.WebElementContainer;
import de.telekom.testframework.selenium.annotations.Ignore;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel
 */
public class DelegatedWebElement extends WebElementContainer implements WebElement {

    @Ignore
    protected final WebElement webElement;
    protected final FieldElementLocator locator;

    public DelegatedWebElement(WebDriver webDriver, FieldElementLocator locator, WebElement webElement) {
        super(webDriver, null, true);

        Objects.requireNonNull(webDriver, "WebElement is null");
        Objects.requireNonNull(webElement, "WebElement is null");

        this.webElement = webElement;
        this.locator = locator;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    @Override
    public String toString() {

        String s = "";

        SearchContext sc = locator.searchContextGetter.getSearchContext();

        if (sc instanceof DelegatedWebElement) {
            s += ((DelegatedWebElement) sc).toString() + "/";
        } else if (sc instanceof WebElement) {
            s += sc.toString() + "/";
        }

        s += this.getClass().getSimpleName() + " " + this.locator.toString();

        return s;
    }

    void handle(String action, Runnable r, Object... args) {
        ActionHandler.handle(this, toString(), action, r, args);
    }

    @Override
    public void click() {
        handle("click", new Runnable() {
            @Override
            public void run() {
                webElement.click();
            }
        });
    }

    @Override
    public void submit() {
        handle("submit", new Runnable() {
            @Override
            public void run() {
                webElement.submit();
            }
        });
    }

    @Override
    public void sendKeys(final CharSequence... keysToSend) {
        handle("sendKeys", new Runnable() {
            @Override
            public void run() {
                webElement.sendKeys(keysToSend);
            }
        }, (Object[]) keysToSend);
    }

    @Override
    public void clear() {
        handle("clear", new Runnable() {
            @Override
            public void run() {
                webElement.clear();
            }
        });
    }

    @Override
    public String getTagName() {
        return webElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return webElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return webElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    @Override
    public String getText() {
        return webElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        //return findAll(WebElement.class, by);
        return webElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        //return find(WebElement.class, by);
        return webElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return webElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return webElement.getSize();
    }

    @Override
    public String getCssValue(String propertyName) {
        return webElement.getCssValue(propertyName);
    }

    public boolean exists() {
        getWebDriver().manage().timeouts().implicitlyWait(SeleniumConfiguration.current.existsTimeout, TimeUnit.SECONDS);
        try {
            return !locator.findElements().isEmpty();
        } finally {
            getWebDriver().manage().timeouts().implicitlyWait(SeleniumConfiguration.current.implicitlyWait, TimeUnit.SECONDS);
        }
    }
}
