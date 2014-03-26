package de.telekom.testframework.selenium.internal;

import de.telekom.testframework.selenium.ActionHandler;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 *
 * @author Daniel Biehl
 */
public class WebElementProxy implements InvocationHandler {

    @SuppressWarnings("unchecked")
    public static WebElement createProxy(ClassLoader loader, WebDriver driver, ElementLocator locator) {
        InvocationHandler handler = new WebElementProxy(driver, locator);

        return (WebElement) Proxy.newProxyInstance(loader, new Class<?>[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
    }

    private final WebDriver driver;
    private final ElementLocator locator;

    public WebElementProxy(WebDriver driver, ElementLocator locator) {
        this.driver = driver;
        this.locator = locator;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
        if ("toString".equals(method.getName())) {
            return locator.toString();
        }

        return realInvoke(method, objects, true);
    }

    private Object realInvoke(Method method, Object[] objects, boolean catchStale) throws Throwable {
        WebElement element;

        ActionHandler.waitForPageLoaded(driver, locator);
        ActionHandler.needWaitForPageLoad(false);
        try {
            element = locator.findElement();
        } finally {
            ActionHandler.needWaitForPageLoad(true);
        }
        if ("getWrappedElement".equals(method.getName())) {
            return element;
        }

        try {
            return method.invoke(element, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            Throwable cause = e.getCause();
            // try again if the cause is a StaleElementReferenceException
            if (cause instanceof StaleElementReferenceException) {
                if (catchStale) {
                    return realInvoke(method, objects, false);
                }
            }
            throw cause;

        }
    }
}
