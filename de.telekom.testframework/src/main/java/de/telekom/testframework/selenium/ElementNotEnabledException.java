package de.telekom.testframework.selenium;

import org.openqa.selenium.WebDriverException;

/**
 *
 * @author Daniel
 */
public class ElementNotEnabledException extends WebDriverException {
    private static final long serialVersionUID = 1L;

    public ElementNotEnabledException() {
        super();
    }

    public ElementNotEnabledException(String message) {
        super(message);
    }

    public ElementNotEnabledException(Throwable cause) {
        super(cause);
    }

    public ElementNotEnabledException(String message, Throwable cause) {
        super(message, cause);
    }

}
