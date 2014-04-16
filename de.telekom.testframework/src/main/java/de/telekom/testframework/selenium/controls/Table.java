package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel Biehl
 */
public class Table extends Control {

    public Table(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    public List<Row> getRows() {
        return getRows(Row.class);
    }

    public <T extends Row> List<T> getRows(Class<T> cls) {
        return findAll(cls, By.xpath("./tr | ./tbody/tr"));
    }
}
