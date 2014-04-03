package de.telekom.testframework.selenium;

import org.openqa.selenium.WebDriverException;

/**
 *
 * @author Daniel
 */
public class CantCloseWindowException extends WebDriverException {
    private static final long serialVersionUID = 1L;

    public CantCloseWindowException() {
        super();
    }

    public CantCloseWindowException(String message) {
        super(message);
    }

    public CantCloseWindowException(Throwable cause) {
        super(cause);
    }

    public CantCloseWindowException(String message, Throwable cause) {
        super(message, cause);
    }

}
