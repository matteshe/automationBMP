package de.telekom.testframework.selenium;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Daniel Biehl
 */
public class Mask extends WebElementContainer {

    public Mask(WebDriver webdriver) {
        super(webdriver);
    }

    public Mask(WebDriver webdriver, SearchContext searchContext) {
        super(webdriver, searchContext);
    }

}
