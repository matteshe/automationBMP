package de.telekom.testframework.selenium;

import de.telekom.testframework.selenium.annotations.Properties;
import de.telekom.testframework.selenium.annotations.UseParent;
import de.telekom.testframework.selenium.controls.DelegatedWebElement;
import de.telekom.testframework.selenium.internal.ElementLocatorFactoryImpl;
import de.telekom.testframework.selenium.internal.FieldDecoratorImpl;
import de.telekom.testframework.selenium.internal.FieldElementLocator;
import de.telekom.testframework.selenium.internal.FieldElementLocator.SearchContextWrapper;
import de.telekom.testframework.selenium.internal.ListOfWebElementProxy;
import de.telekom.testframework.selenium.internal.WebElementProxy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

/**
 * Self initializing Container class. All fields of type WebElement where
 * decorated with a Proxy for a WebElement. Base class for all Masks, Pages and
 * Controls
 *
 * @author Daniel Biehl
 */
public abstract class WebElementContainer implements WebDriverWrapper {

    public WebElementContainer(WebDriver webdriver, SearchContext searchContext) {
        this(webdriver, searchContext, false);
    }

    public WebElementContainer(WebDriver webdriver, SearchContext searchContext, boolean useThisAsSearchContext) {
        Objects.requireNonNull(webdriver, "webdriver is null");

        this.webDriver = webdriver;

        SearchContext sc = searchContext;

        if (useThisAsSearchContext) {
            sc = (SearchContext) this;
        } else {
            if (sc == null) {
                sc = webdriver;
            }
        }
        this.searchContext = sc;

        initElements();
    }

    public WebElementContainer(WebDriver webdriver) {
        this(webdriver, null);
    }

    protected static final Logger logger = Logger.getLogger(Page.class.getName());
    protected final WebDriver webDriver;
    protected final SearchContext searchContext;
    protected boolean hasResourceBundle = false;
    protected ResourceBundle resourceBundle = null;
    protected Field parentElementField = null;

    FieldElementLocator.SearchContextWrapper defaultSearchContextWrapper = new FieldElementLocator.SearchContextWrapper() {
        @Override
        public SearchContext getSearchContext() {
            return WebElementContainer.this.getSearchContext();
        }
    };

    FieldElementLocator.SearchContextWrapper simpleSearchContextWrapper = new FieldElementLocator.SearchContextWrapper() {
        @Override
        public SearchContext getSearchContext() {
            return WebElementContainer.this.searchContext;
        }
    };

    private SearchContext getSearchContext() {
        if (parentElementField != null) {
            try {
                parentElementField.setAccessible(true);
                Object o = parentElementField.get(WebElementContainer.this);
                return (WebElement) o;
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.warning(ex.getMessage());
            }
        }
        return searchContext;
    }

    @Override
    public WebDriver getWebDriver() {
        return webDriver;
    }

    void handle(String action, Runnable r, Object... args) {
        ActionHandler.handle(this, this.getClass().getSimpleName(), action, r, args);
    }

    public ResourceBundle getResourceBundle() {
        if (!hasResourceBundle) {
            hasResourceBundle = true;
            try {
                String propName = this.getClass().getName();
                Properties a = this.getClass().getAnnotation(Properties.class);
                if (a != null) {
                    if (!a.value().isEmpty()) {
                        propName = a.value();
                    }
                }
                resourceBundle = ResourceBundle.getBundle(propName);
            } catch (Exception e) {
                //logger.info(e.getMessage());
            }
        }
        return resourceBundle;
    }

    public FieldElementLocator.SearchContextWrapper getSearchContextForField(Field field) {
        if (field.isAnnotationPresent(UseParent.class)) {
            String n = field.getAnnotation(UseParent.class).value();
            if (n == null || n.isEmpty()) {
                n = "parent";
            }
            try {
                final Field f = this.getClass().getDeclaredField(n);
                if (!WebElement.class.isAssignableFrom(f.getType())) {
                    throw new RuntimeException("UseParent annotated field " + f.toString() + " must be of type WebElement");
                }
                return new FieldElementLocator.SearchContextWrapper() {
                    @Override
                    public SearchContext getSearchContext() {
                        try {
                            f.setAccessible(true);
                            Object o = f.get(WebElementContainer.this);
                            if (o != null) {
                                return (WebElement) o;
                            } else {
                                throw new RuntimeException("UseParent field is null for field " + f.toString());
                            }
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            logger.warning(ex.getMessage());
                        }
                        return WebElementContainer.this.getSearchContext();
                    }
                };
            } catch (NoSuchFieldException | SecurityException ex) {
                throw new RuntimeException("cannot access field " + n + " in class " + this.getClass(), ex);
            }
        }
        if (parentElementField != null) {
            if (parentElementField.getName().equals(field.getName())) {
                return simpleSearchContextWrapper;
            }
        }
        return defaultSearchContextWrapper;
    }

    protected final void initElements() {
        if (this.getClass().isAnnotationPresent(UseParent.class)) {
            String n = this.getClass().getAnnotation(UseParent.class).value();
            if (n == null || n.isEmpty()) {
                n = "parent";
            }
            try {
                parentElementField = this.getClass().getDeclaredField(n);
                if (!WebElement.class.isAssignableFrom(parentElementField.getType())) {
                    throw new RuntimeException("UseParent annotated field " + parentElementField.toString() + " must be of type WebElement");
                }
            } catch (NoSuchFieldException | SecurityException ex) {
                throw new RuntimeException("cannot access field " + n + " in class " + this.getClass(), ex);
            }
        }

        ElementLocatorFactory factory = new ElementLocatorFactoryImpl(this);
        FieldDecorator decorator = new FieldDecoratorImpl(factory, this);
        PageFactory.initElements(decorator, this);
    }

    public static Constructor<?> getDelegatedElementConstructor(Class<?> clz) throws RuntimeException {
        Constructor<?> constructor = null;
        try {
            if (!DelegatedWebElement.class.isAssignableFrom(clz)) {
                clz = DelegatedWebElement.class;
            }
            constructor = clz.getConstructor(WebDriver.class, FieldElementLocator.class, WebElement.class);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException("cannot find a valid constructor '" + clz.getName() + "(WebDriver, FieldElementLocator, WebElement)'", ex);
        }
        return constructor;
    }

    public WebElement find(By by) {
        return find(WebElement.class, by);
    }

    @SuppressWarnings("unchecked")
    public <T extends WebElement> T find(Class<T> clz, By by) {
        try {

            Constructor<?> constructor = WebElementContainer.getDelegatedElementConstructor(clz);

            FieldElementLocator locator = new FieldElementLocator(defaultSearchContextWrapper, clz, by);
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

        FieldElementLocator locator = new FieldElementLocator(defaultSearchContextWrapper, clz, by);
        List<T> list = ListOfWebElementProxy.createProxy(ClassLoader.getSystemClassLoader(), webDriver, constructor, locator);

        return list;
    }
}
