package de.telekom.testframework.selenium.internal;

import de.telekom.testframework.selenium.ActionHelper;
import de.telekom.testframework.selenium.WebDriverWrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 *
 * @author Daniel Biehl
 */
public class ListOfWebElementProxy implements InvocationHandler {

    @SuppressWarnings("unchecked")
    public static <T> List<T> createProxy(ClassLoader loader, WebDriver driver, Constructor<?> constructor, ElementLocator locator) {
        InvocationHandler handler = new ListOfWebElementProxy(driver, constructor, locator);

        return (List<T>) Proxy.newProxyInstance(loader, new Class<?>[]{List.class, WebDriverWrapper.class}, handler);
    }

    private final ElementLocator locator;
    private final WebDriver driver;
    private final Constructor<?> constructor;

    public ListOfWebElementProxy(WebDriver driver, Constructor<?> constructor, ElementLocator locator) {
        this.driver = driver;
        this.constructor = constructor;
        this.locator = locator;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {

        if ("toString".equals(method.getName())) {
            return "List<?> " + locator.toString();
        }

        if ("getWebDriver".equals(method.getName())) {
            return driver;
        }

        return realInvoke(method, objects, true);
    }

    private Object realInvoke(Method method, Object[] objects, boolean catchStale) throws Throwable {
        List<WebElement> elements;
        ActionHelper.waitUntilPageLoaded(driver, locator);
        ActionHelper.needWaitForPageLoad(false);
        try {
            elements = locator.findElements();
        } finally {
            ActionHelper.needWaitForPageLoad(true);
        }
        List<Object> newElements = new ArrayList<>(elements.size());
        int index = 0;
        for (WebElement element : elements) {
            newElements.add(createWebElement(element, index));
            index++;
        }

        try {
            return method.invoke(newElements, objects);
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

    private Object createWebElement(WebElement element, int index) {

        try {
            if (locator instanceof FieldElementLocator) {
                return constructor.newInstance(driver, new IndexedElementLocator((FieldElementLocator) locator, index), element);
            }
            return constructor.newInstance(driver, locator, element);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("cannot instanciate '" + constructor.getDeclaringClass().getName() + "(WebDriver, WebElement)'", ex);
        }
    }
}
