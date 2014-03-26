package de.telekom.testframework.selenium.internal;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 *
 * @author Daniel Biehl
 */
public class FieldElementLocator implements ElementLocator {

    class MyAnnotations extends Annotations {

        public MyAnnotations(Field field) {
            super(field);
        }

        public String replaceParameters(String input, Object... args) {
            return String.format(input, args);
        }

        @Override
        protected By buildByFromShortFindBy(FindBy findBy) {
            if (FieldElementLocator.this.parameterGetter != null) {
                Object[] params = FieldElementLocator.this.parameterGetter.getParameters();

                if (!"".equals(findBy.className())) {
                    return By.className(replaceParameters(findBy.className(), params));
                }

                if (!"".equals(findBy.css())) {
                    return By.cssSelector(replaceParameters(findBy.css(), params));
                }

                if (!"".equals(findBy.id())) {
                    return By.id(replaceParameters(findBy.id(), params));
                }

                if (!"".equals(findBy.linkText())) {
                    return By.linkText(replaceParameters(findBy.linkText(), params));
                }

                if (!"".equals(findBy.name())) {
                    return By.name(replaceParameters(findBy.name(), params));
                }

                if (!"".equals(findBy.partialLinkText())) {
                    return By.partialLinkText(replaceParameters(findBy.partialLinkText(), params));
                }

                if (!"".equals(findBy.tagName())) {
                    return By.tagName(replaceParameters(findBy.tagName(), params));
                }

                if (!"".equals(findBy.xpath())) {
                    return By.xpath(replaceParameters(findBy.xpath(), params));
                }

                // Fall through
                return null;
            }
            return super.buildByFromShortFindBy(findBy);
        }

        @Override
        protected By buildByFromLongFindBy(FindBy findBy) {
            if (FieldElementLocator.this.parameterGetter != null) {
                How how = findBy.how();
                String using = replaceParameters(findBy.using());

                switch (how) {
                    case CLASS_NAME:
                        return By.className(using);

                    case CSS:
                        return By.cssSelector(using);

                    case ID:
                        return By.id(using);

                    case ID_OR_NAME:
                        return new ByIdOrName(using);

                    case LINK_TEXT:
                        return By.linkText(using);

                    case NAME:
                        return By.name(using);

                    case PARTIAL_LINK_TEXT:
                        return By.partialLinkText(using);

                    case TAG_NAME:
                        return By.tagName(using);

                    case XPATH:
                        return By.xpath(using);

                    default:
                        // Note that this shouldn't happen (eg, the above matches all
                        // possible values for the How enum)
                        throw new IllegalArgumentException("Cannot determine how to locate element " + field);
                }
            }
            return super.buildByFromLongFindBy(findBy);
        }
    }

    public final FieldSearchContextGetter searchContextGetter;

    PageResourceLocatorBuilder builder;

    private By by;

    public final Field field;

    public FieldElementLocator(FieldSearchContextGetter searchContext, By by) {
        Objects.requireNonNull(searchContext);
        this.searchContextGetter = searchContext;

        this.field = null;
        this.by = by;
    }

    /**
     * Creates a new element locator.
     *
     * @param searchContext The context to use when finding the element
     * @param field The field on the Page Object that will hold the located
     * value
     */
    public FieldElementLocator(FieldSearchContextGetter searchContext, Field field) {
        Objects.requireNonNull(searchContext);
        Objects.requireNonNull(field);

        this.searchContextGetter = searchContext;

        this.field = field;

        Annotations annotations = new MyAnnotations(field);

        this.by = annotations.buildBy();
    }

    /**
     *
     * @param searchContext
     * @param field
     * @param builder
     */
    public FieldElementLocator(FieldSearchContextGetter searchContext, Field field, PageResourceLocatorBuilder builder) {
        Objects.requireNonNull(searchContext);
        Objects.requireNonNull(field);
        Objects.requireNonNull(builder);
        this.field = field;

        this.searchContextGetter = searchContext;

        this.builder = builder;
        this.by = builder.by();
    }

    private ParameterGetter parameterGetter = null;

    public void setParameterGetter(ParameterGetter getter) {
        this.parameterGetter = getter;
    }

    /**
     * @return the by
     */
    public By getBy() {

        if (parameterGetter != null) {
            if (builder != null) {
                Object[] p = parameterGetter.getParameters();
                if (p.length > 0) {
                    by = builder.by(p[0].toString());
                }
            } else {
                if (field != null) {
                    by = new MyAnnotations(field).buildBy();
                }
            }

        }
        return by;
    }

    /**
     * Find the element.
     *
     * @return
     */
    @Override
    public WebElement findElement() {
        WebElement element = searchContextGetter.getSearchContext().findElement(getBy());

        return element;
    }

    /**
     * Find the element list.
     *
     * @return
     */
    @Override
    public List<WebElement> findElements() {
        List<WebElement> elements = searchContextGetter.getSearchContext().findElements(getBy());

        return elements;
    }

    @Override
    public String toString() {
        String result = "";

        if (field != null) {
            result += field.getDeclaringClass().getSimpleName() + "." + field.getName();
        }

        return result + "[" + getBy().toString() + "]";
    }
}
