package de.telekom.testframework.selenium.controls;

import de.telekom.testframework.selenium.internal.FieldElementLocator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel
 */
public class Select extends Control {

    public Select(WebDriver driver, FieldElementLocator locator, WebElement webElement) {
        super(driver, locator, webElement);
    }

    public List<Option> getOptions() {
        return findAll(Option.class, By.tagName("option"));
    }

    public boolean isMultiple() {
        String m = getAttribute("multiple");
        return (m != null && !"false".equals(m));
    }

    protected void internalClearSelection() {
        if (isMultiple()) {
            for (Option option : getOptions()) {
                option.unselect();
            }
        }
    }

    public void clearSelection() {
        handle("select", new Runnable() {

            @Override
            public void run() {
                internalClearSelection();
            }
        });
    }

    protected void internalSelect(final int index, final boolean clearSelection) {

        if (clearSelection) {
            internalClearSelection();
        }

        findItem(index).select();
    }

    protected void internalSelect(final String textOrValue, final boolean clearSelection) {
        if (clearSelection) {
            internalClearSelection();
        }

        findItem(textOrValue).select();
    }

    public void select(String... textOrValues) {
        select(true, textOrValues);
    }

    public void select(final boolean clearSelection, final String... textOrValues) {
        List<Object> args = new ArrayList<>();
        args.add(clearSelection);
        args.addAll(Arrays.asList(textOrValues));

        handle("select", new Runnable() {

            @Override
            public void run() {
                if (clearSelection) {
                    internalClearSelection();
                }

                for (String s : textOrValues) {
                    internalSelect(s, false);
                }
            }

        }, args.toArray());
    }

    public void select(final Integer... indexes) {
        select(true, indexes);
    }

    public void select(final boolean clearSelection, final Integer... indexes) {
        List<Object> args = new ArrayList<>();
        args.add(clearSelection);
        args.addAll(Arrays.asList(indexes));

        handle("select", new Runnable() {

            @Override
            public void run() {
                if (clearSelection) {
                    internalClearSelection();
                }

                for (int s : indexes) {
                    internalSelect(s, false);
                }

            }
        }, args.toArray());
    }

    public void select(final Object[] values) {
        select(true, values);
    }

    public void select(final boolean clearSelection, final Object[] values) {
        List<Object> args = new ArrayList<>();
        args.add(clearSelection);
        args.addAll(Arrays.asList(values));

        handle("select", new Runnable() {

            @Override
            public void run() {
                if (clearSelection) {
                    internalClearSelection();
                }

                for (Object s : values) {
                    if (s instanceof Integer) {
                        internalSelect((Integer) s, false);
                    } else {
                        internalSelect(s.toString(), false);
                    }
                }
            }
        }, args.toArray());
    }

    @Override
    public void set(Object value) {

        if (value instanceof Integer) {
            select((Integer) value);
            return;
        }

        if (value instanceof String) {
            select((String) value);
            return;
        }

        if (value instanceof String[]) {
            select((String[]) value);
            return;
        }

        if (value.getClass().isArray()) {
            List<Object> vs = new ArrayList<>();
            for (int i = 0; i < Array.getLength(value); i++) {
                vs.add(Array.get(value, i));
            }

            select(vs.toArray());
            return;
        }

        if (value instanceof Iterable<?>) {

            List<Object> vs = new ArrayList<>();
            for (Object o : (Iterable<?>) value) {
                vs.add(o);
            }

            select(vs.toArray());
            return;
        }

        select(value.toString());
    }

    public List<Option> getSelectedOptions() {
        List<Option> result = new ArrayList<>();
        for (Option o : getOptions()) {
            if (o.isSelected()) {
                result.add(o);
            }
        }
        return result;
    }

    public Option getSelectedOption() {
        for (Option o : getSelectedOptions()) {
            return o;            
        }
        return null;
    }
    
    public String getSelectedValue() {
        for (Option o : getSelectedOptions()) {
            return o.getValue();
        }
        return null;
    }

    public List<String> getSelectedValues() {
        List<String> result = new ArrayList<>();
        for (Option o : getSelectedOptions()) {
            result.add(o.getValue());
            break;
        }
        return result;
    }

    public String getSelectedText() {
        for (Option o : getSelectedOptions()) {
            return o.getText();
        }
        return null;
    }

    public List<String> getSelectedTexts() {
        List<String> result = new ArrayList<>();
        for (Option o : getSelectedOptions()) {
            result.add(o.getText());
            break;
        }
        return result;
    }

    public int getSelectedIndex() {
        for (Option o : getSelectedOptions()) {
            return o.getIndex();
        }
        return -1;
    }

    public List<Integer> getSelectedIndexes() {
        List<Integer> result = new ArrayList<>();
        for (Option o : getSelectedOptions()) {
            result.add(o.getIndex());
            break;
        }

        return result;
    }

    public Option findItem(String textOrValue) {
        for (Option option : getOptions()) {
            String s = option.getText();

            if (s != null && s.matches(textOrValue)) {
                return option;
            }
            s = option.getValue();
            if (s != null && s.matches(textOrValue)) {
                return option;
            }
        }
        throw new NoSuchElementException("cannot locate an option element with text or value " + textOrValue);
    }

    public Option findItem(int index) {
        for (Option option : getOptions()) {
            if (option.getIndex() == index) {
                return option;
            }
        }
        throw new NoSuchElementException("cannot locate an option element with index " + index);
    }
}
