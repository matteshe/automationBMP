package de.telekom.testframework.selenium;

import de.telekom.testframework.selenium.controls.CheckBox;
import de.telekom.testframework.selenium.controls.Control;
import de.telekom.testframework.selenium.controls.DelegatedWebElement;
import de.telekom.testframework.selenium.controls.Select;
import de.telekom.testframework.selenium.matchers.CachedElementTypeSafeMatcher;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.is;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Daniel Biehl
 */
public class Matchers {

    @Factory
    public static Matcher<WebElement> attribute(final String attributeName, Matcher<String> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, String>("attribute '" + attributeName + "'", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                return item.getAttribute(attributeName);
            }
        };
    }

    @Factory
    public static Matcher<WebElement> text(final Matcher<String> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, String>("text", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                return item.getText();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> innerHTML(final Matcher<String> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, String>("innerHTML", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                return item.getAttribute("innerHTML");
            }
        };
    }

    @Factory
    public static Matcher<WebElement> textContent(final Matcher<String> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, String>("textContent", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                return item.getAttribute("textContent");
            }
        };
    }

    @Factory
    public static <T> Matcher<WebElement> value(final Matcher<T> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, T>("value", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                if (item instanceof Control) {
                    return ((Control) item).get();
                }
                return item.getAttribute("value");
            }
        };
    }

    @Factory
    public static Matcher<WebElement> displayed(final Matcher<Boolean> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, Boolean>("displayed", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                return item.isDisplayed();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> displayed() {
        return new CachedElementTypeSafeMatcher<WebElement, Boolean>("displayed") {

            @Override
            protected Object getValue(WebElement item) {
                return item.isDisplayed();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> enabled(final Matcher<Boolean> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, Boolean>("enabled", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                return item.isEnabled();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> enabled() {
        return new CachedElementTypeSafeMatcher<WebElement, Boolean>("enabled") {

            @Override
            protected Object getValue(WebElement item) {
                return item.isEnabled();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> selected(final Matcher<Boolean> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, Boolean>("selected", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                return item.isSelected();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> selected() {
        return new CachedElementTypeSafeMatcher<WebElement, Boolean>("selected") {

            @Override
            protected Object getValue(WebElement item) {
                return item.isSelected();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> exists(final Matcher<Boolean> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, Boolean>("exists", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                if (item instanceof DelegatedWebElement) {
                    return ((DelegatedWebElement) item).exists();
                }
                throw new UnsupportedOperationException();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> exists() {
        return new CachedElementTypeSafeMatcher<WebElement, Boolean>("exists") {

            @Override
            protected Object getValue(WebElement item) {
                if (item instanceof DelegatedWebElement) {
                    return ((DelegatedWebElement) item).exists();
                }
                throw new UnsupportedOperationException();
            }
        };
    }

    @Factory
    public static Matcher<WebElement> tagName(final Matcher<String> matcher) {
        return new CachedElementTypeSafeMatcher<WebElement, String>("tag name", matcher) {

            @Override
            protected Object getValue(WebElement item) {
                return item.getTagName();
            }
        };
    }

    @Factory
    public static Matcher<Page> currentPage(final Matcher<Boolean> matcher) {
        return new CachedElementTypeSafeMatcher<Page, Boolean>("current page", matcher) {

            @Override
            protected Object getValue(Page item) {
                return item.isCurrentPage();
            }
        };
    }

    @Factory
    public static Matcher<Page> currentPage() {
        return new CachedElementTypeSafeMatcher<Page, Boolean>("current page") {

            @Override
            protected Object getValue(Page item) {
                return item.isCurrentPage();
            }
        };
    }

    /**
     *
     * @return @deprecated use {@code is(currentPage))}
     */
    @Factory
    @Deprecated
    public static Matcher<Page> isCurrentPage() {
        return is(currentPage());
    }

    @Factory
    public static Matcher<Page> loaded(final Matcher<Boolean> matcher) {
        return new CachedElementTypeSafeMatcher<Page, Boolean>("loaded", matcher) {

            @Override
            protected Object getValue(Page item) {
                return item.isLoaded();
            }
        };
    }

    @Factory
    public static Matcher<Page> loaded() {
        return new CachedElementTypeSafeMatcher<Page, Boolean>("loaded") {

            @Override
            protected Object getValue(Page item) {
                return item.isLoaded();
            }
        };
    }

    @Factory
    public static Matcher<Select> selectedItem(final Matcher<WebElement> matcher) {
        return new CachedElementTypeSafeMatcher<Select, WebElement>("selected item", matcher) {

            @Override
            protected Object getValue(Select item) {
                return item.getSelectedOption();
            }
        };
    }

    @Factory
    public static Matcher<Select> selectedItems(final Matcher<List<WebElement>> matcher) {
        return new CachedElementTypeSafeMatcher<Select, List<WebElement>>("selected items", matcher) {

            @Override
            protected Object getValue(Select item) {
                return item.getSelectedOptions();
            }
        };
    }

    @Factory
    public static Matcher<Select> selectedItemText(final Matcher<String> matcher) {
        return new CachedElementTypeSafeMatcher<Select, String>("selected item text", matcher) {

            @Override
            protected Object getValue(Select item) {
                return item.getSelectedText();
            }
        };
    }

    @Factory
    public static Matcher<Select> selectedItemValue(final Matcher<String> matcher) {
        return new CachedElementTypeSafeMatcher<Select, String>("selected item value", matcher) {

            @Override
            protected Object getValue(Select item) {
                return item.getSelectedValue();
            }
        };
    }

    @Factory
    public static Matcher<Select> selectedItemIndex(final Matcher<Integer> matcher) {
        return new CachedElementTypeSafeMatcher<Select, Integer>("selected item index", matcher) {

            @Override
            protected Object getValue(Select item) {
                return item.getSelectedIndex();
            }
        };
    }

    @Factory
    public static Matcher<Select> item(final String textOrValue, final Matcher<WebElement> matcher) {
        return new CachedElementTypeSafeMatcher<Select, WebElement>("item '" + textOrValue + "'", matcher) {

            @Override
            protected Object getValue(Select item) {
                return item.findItem(textOrValue);
            }
        };
    }

    @Factory
    public static Matcher<Select> item(final int index, final Matcher<WebElement> matcher) {
        return new CachedElementTypeSafeMatcher<Select, WebElement>("item " + index, matcher) {

            @Override
            protected Object getValue(Select item) {
                return item.findItem(index);
            }
        };
    }

    /**
     * Creates a matcher that matches if the examined Browsers current page
     * matches the given <code>matcher</code>.
     *
     * @param matcher
     * @return
     */
    @Factory
    public static Matcher<Browser> currentUrl(Matcher<String> matcher) {
        return new CachedElementTypeSafeMatcher<Browser, String>("current url", matcher) {

            @Override
            protected String getValue(Browser item) {
                return item.getCurrentUrl();
            }
        };
    }

    /**
     * Creates a matcher that matches if the examined CheckBox checked state
     * matches the given <code>matcher</code>.
     *
     * @param matcher
     * @return
     */
    @Factory
    public static Matcher<CheckBox> checked(final Matcher<Boolean> matcher) {
        return new CachedElementTypeSafeMatcher<CheckBox, Boolean>("checked", matcher) {

            @Override
            protected Object getValue(CheckBox item) {
                return item.isChecked();
            }
        };
    }

    /**
     * Creates a matcher that matches if the examined CheckBox is checked.
     *
     * @return
     */
    @Factory
    public static Matcher<CheckBox> checked() {
        return new CachedElementTypeSafeMatcher<CheckBox, Boolean>("checked") {

            @Override
            protected Object getValue(CheckBox item) {
                return item.isChecked();
            }
        };
    }

    /**
     * Creates a matcher that matches if the examined string is a valid absolute
     * url.
     *
     * @return
     */
    @Factory
    public static Matcher<String> url() {
        return new CachedElementTypeSafeMatcher<String, Boolean>("an url") {

            @Override
            protected Object getValue(String value) {

                try {
                    URL url = new URL(value);
                    return url.toURI().isAbsolute();
                } catch (MalformedURLException | URISyntaxException ex) {

                }

                return false;
            }
        };
    }
}
