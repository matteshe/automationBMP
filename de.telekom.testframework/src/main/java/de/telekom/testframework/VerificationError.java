package de.telekom.testframework;

/**
 *
 * @author Daniel Biehl
 */
public class VerificationError extends AssertionError {
    private static final long serialVersionUID = 1L;
 
    public VerificationError() {
    }

    public VerificationError(String message) {
        super(message);
    }

    public VerificationError(String message, Throwable cause) {
        super(message, cause);
    }
}
