package de.telekom.testframework.selenium.internal;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

/**
 *
 * @author Daniel Biehl
 */
public class PageResourceLocatorBuilder {

    public static final String PARAMETER_STRING = "$parameter$";

    public final String expression;
    public final How how;

    public PageResourceLocatorBuilder(String expression, String how) {
        this.expression = expression;
        if (how == null) {
            this.how = How.valueOf("LINK_TEXT");
        } else {
            this.how = How.valueOf(how);
        }
    }

    public static String replaceParamter(String locator, String parameter) {
        if (locator.contains(PageResourceLocatorBuilder.PARAMETER_STRING)) {
            if ((parameter == null) || (parameter.isEmpty())) {
                throw new IllegalArgumentException("locator '" + locator + "' needs a parameter");
            }

            return locator.replace(PageResourceLocatorBuilder.PARAMETER_STRING, parameter);
        }
        return locator;
    }

    public By by(String parameter) {
        String locator = replaceParamter(expression, parameter);

        if (how.equals(How.ID)) {
            return By.id(locator);
        }
        if (how.equals(How.NAME)) {
            return By.name(locator);
        }
        if (how.equals(How.XPATH)) {
            return By.xpath(locator);
        }
        if (how.equals(How.PARTIAL_LINK_TEXT)) {
            return By.partialLinkText(locator);
        }
        if (how.equals(How.LINK_TEXT)) {
            return By.linkText(locator);
        }
        if (how.equals(How.CLASS_NAME)) {
            return By.className(locator);
        }
        if (how.equals(How.CSS)) {
            return By.cssSelector(locator);
        }

        throw new IllegalArgumentException("Location type not supported");
    }

    public By by() {
        return by("");
    }
}
