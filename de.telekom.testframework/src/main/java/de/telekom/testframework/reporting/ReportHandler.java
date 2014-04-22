package de.telekom.testframework.reporting;

/**
 *
 * @author Daniel Biehl
 */
public interface ReportHandler {

    void startTest(String name);

    void endTest(String name);

    void entering(String contextName, String actionName, Object... args);

    void exiting(String contextName, String actionName);

    public void reportMessage(String message);

    public void reportFatal(String message);

    public void reportError(String message);

    public void reportInfo(String message);

    public void reportWarning(String message);

    public void reportVerification(String message, boolean result, Throwable exception);

    public void reportAssertion(String message, boolean result, Throwable exception);

    public void reportCheck(String message, boolean result);

    public void reportException(Throwable t);

    public void reportFileReference(String filename);

    public void reportScreenshot(byte[] bytes);
}
