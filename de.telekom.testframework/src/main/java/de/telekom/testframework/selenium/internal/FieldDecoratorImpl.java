package de.telekom.testframework.selenium.internal;

import de.telekom.testframework.selenium.WebElementContainer;
import de.telekom.testframework.selenium.Parameterized;
import de.telekom.testframework.selenium.annotations.Ignore;
import de.telekom.testframework.selenium.controls.DelegatedWebElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 *
 * @author Daniel Biehl
 */
public class FieldDecoratorImpl implements FieldDecorator {

    private final WebElementContainer container;
    protected ElementLocatorFactory factory;

    public FieldDecoratorImpl(ElementLocatorFactory factory, final WebElementContainer container) {
        this.factory = factory;
        this.container = container;
    }

    private boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }
        // Type erasure in Java isn't complete. Attempt to discover the generic
        // type of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }
        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
        if (!(listType instanceof Class<?>)) {
            return false;
        }
        Class<?> clz = (Class<?>) listType;
        if (!DelegatedWebElement.class.isAssignableFrom(clz) && !WebElement.class.isAssignableFrom(clz)) {
            return false;
        }
        //                if (field.getAnnotation(FindBy.class) == null
        //                        && field.getAnnotation(FindBys.class) == null
        //                        && field.getAnnotation(FindAll.class) == null) {
        //                    return false;
        //                }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object decorate(ClassLoader loader, Field field) {
        if (field.isAnnotationPresent(Ignore.class)) {
            return null;
        }

        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        if (Parameterized.class.isAssignableFrom(field.getType())) {
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType)) {
                return false;
            }

            Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];

            if (type instanceof ParameterizedType) {

                Type listType = ((ParameterizedType) type).getRawType();

                if (!(listType instanceof Class<?>)) {
                    return null;
                }

                Class<?> listClz = (Class<?>) listType;
                if (List.class.isAssignableFrom(listClz)) {
                    Type elementType = ((ParameterizedType) type).getActualTypeArguments()[0];
                    if (!(elementType instanceof Class<?>)) {
                        return null;
                    }

                    Class<?> clz = (Class<?>) elementType;
                    Constructor<?> constructor = WebElementContainer.getDelegatedElementConstructor(clz);

                    List<?> element = ListOfWebElementProxy.createProxy(loader, container.getWebDriver(), constructor, locator);

                    return new ParameterizedImpl<>(container.getWebDriver(), (FieldElementLocator) locator, (List<?>) element);
                }
            }

            if (!(type instanceof Class<?>)) {
                return null;
            }

            Class<?> clz = (Class<?>) type;
            Constructor<?> constructor = WebElementContainer.getDelegatedElementConstructor(clz);

            WebElement element = WebElementProxy.createProxy(loader, container.getWebDriver(), locator);
            try {
                return new ParameterizedImpl<>(container.getWebDriver(), (FieldElementLocator) locator, (WebElement) constructor.newInstance(container.getWebDriver(), (FieldElementLocator) locator, element));
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException("cannot instanciate '" + field.getType().getName() + "(WebDriver, WebElement)'", ex);
            }
        }

        if (!DelegatedWebElement.class.isAssignableFrom(field.getType()) && !WebElement.class.isAssignableFrom(field.getType()) && !isDecoratableList(field)) {
            return null;
        }

        if (List.class.isAssignableFrom(field.getType())) {
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType)) {
                return false;
            }

            Type elementType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
            if (!(elementType instanceof Class<?>)) {
                return null;
            }

            Class<?> clz = (Class<?>) elementType;
            Constructor<?> constructor = WebElementContainer.getDelegatedElementConstructor(clz);

            return ListOfWebElementProxy.createProxy(loader, container.getWebDriver(), constructor, locator);

        } else {
            Constructor<?> constructor = WebElementContainer.getDelegatedElementConstructor(field.getType());

            if (constructor == null) {
                return null;
            }

            WebElement element = WebElementProxy.createProxy(loader, container.getWebDriver(), locator);
            try {
                return constructor.newInstance(container.getWebDriver(), (FieldElementLocator) locator, element);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException("cannot instanciate '" + field.getType().getName() + "(WebDriver, FieldElementLocator, WebElement)'", ex);
            }
        }
    }

}
