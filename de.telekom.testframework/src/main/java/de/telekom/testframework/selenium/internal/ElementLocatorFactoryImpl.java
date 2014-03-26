package de.telekom.testframework.selenium.internal;

import de.telekom.testframework.selenium.WebElementContainer;
import de.telekom.testframework.selenium.annotations.FindByProperty;
import java.lang.reflect.Field;
import java.util.ResourceBundle;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

/**
 *
 * @author Daniel Biehl
 */
public class ElementLocatorFactoryImpl implements ElementLocatorFactory {

    private final WebElementContainer container;

    public ElementLocatorFactoryImpl(final WebElementContainer container) {
        this.container = container;
    }

    @Override
    public ElementLocator createLocator(final Field field) {

        ResourceBundle rb = container.getResourceBundle();
        if (rb != null) {
            String name = field.getName();
            if (field.getAnnotation(FindByProperty.class) != null) {
                FindByProperty res = field.getAnnotation(FindByProperty.class);
                if (!res.value().isEmpty()) {
                    name = res.value();
                }
            }

            if (rb.containsKey(name)) {
                final String value = rb.getString(name);
                String[] s = value.split(";");
                String v = s[0];
                String how = "ID";
                if (s.length > 1) {
                    how = s[1];
                }

                return new FieldElementLocator(container.getSearchContextForField(field), field, new PageResourceLocatorBuilder(v, how));
            }
        }
        return new FieldElementLocator(container.getSearchContextForField(field), field);
    }

}
