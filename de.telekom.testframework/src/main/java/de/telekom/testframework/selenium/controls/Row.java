package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Row extends Control {

    public Row(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    public List<Column> getColumns() {
        return getColumns(Column.class);
    }

    public <T extends Column> List<T> getColumns(Class<T> cls) {
        return findAll(cls, By.xpath("./td"));
    }
}
