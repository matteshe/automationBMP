package de.telekom.testframework.reporting;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Daniel Biehl
 */
public class ConsoleReportHandler implements ReportHandler {

    int indent = 0;

    private String getIndentString() {
        String s = "";

        for (int i = 0; i < indent; i++) {
            s += "    ";
        }

        return s;
    }

    @Override
    public void startTest(String name) {
        System.out.println(getIndentString() + "Test: " + name);
        indent++;
    }

    @Override
    public void endTest(String name) {
        indent--;
    }

    @Override
    public void entering(String contextName, String actionName, Object... args) {

        String o = "";

        if (actionName != null && !actionName.isEmpty()) {

            o += actionName + " ";
        }

        if (contextName != null && !contextName.isEmpty()) {
            o += contextName;
            o += " ";
        }

        String s = "";
        for (Object a : args) {
            if (!s.isEmpty()) {
                s += ", ";
            }
            if (a == null) {
                s += "null";
            } else {
                s += a.toString();
            }
        }

        o += "(" + s + ")";

        System.out.println(getIndentString() + o);

        indent++;
    }

    @Override
    public void exiting(String contextName, String methodName) {
        indent--;
    }

    @Override
    public void reportMessage(String message) {
        System.out.println(getIndentString() + message);
    }

    @Override
    public void reportFatal(String message) {
        System.out.println(getIndentString() + "FATAL: " + message);
    }

    @Override
    public void reportError(String message) {
        System.out.println(getIndentString() + "ERROR: " + message);
    }

    @Override
    public void reportInfo(String message) {
        System.out.println(getIndentString() + "INFO: " + message);
    }

    @Override
    public void reportWarning(String message) {
        System.out.println(getIndentString() + "WARNING: " + message);
    }

    @Override
    public void reportVerification(String message, boolean result, Throwable exception) {
        if (message == null) {
            message = "";
        }

        String s = message;

        System.out.println(getIndentString() + (result ? "PASSED" : " FAILED") + ": " + s);
    }

    @Override
    public void reportAssertion(String message, boolean result, Throwable exception) {
        if (message == null) {
            message = "";
        }

        String s = message;

        System.out.println(getIndentString() + (result ? "PASSED" : " FAILED") + ": " + s);
    }

    @Override
    public void reportException(Throwable t) {
        StringWriter sw = new StringWriter();

        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String s = sw.toString();
        s = s.replaceAll("\n", "\n" + getIndentString());

        System.out.println(getIndentString() + "Exception: " + s);
    }

    @Override
    public void reportFileReference(String filename) {
        System.out.println(getIndentString() + "REFERENCE: " + filename);
    }

    @Override
    public void reportScreenshot(byte[] bytes) {

    }

}
