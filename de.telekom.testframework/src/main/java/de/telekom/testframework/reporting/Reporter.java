package de.telekom.testframework.reporting;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Biehl
 */
public class Reporter {

    static List<ReportHandler> handlers = new ArrayList<>();

    public static void AddHandler(ReportHandler handler) {
        if (!handlers.contains(handler)) {
            handlers.add(handler);
        }
    }

    static {
        AddHandler(new ConsoleReportHandler());
    }

    public static void startTest(String name) {
        for (ReportHandler handler : handlers) {
            handler.startTest(name);
        }
    }

    public static void endTest(String name) {
        for (ReportHandler handler : handlers) {
            handler.endTest(name);
        }
    }

    public static void entering() {
        entering(null, null);
    }

    public static void entering(String contextName, String methodName, Object... args) {
        for (ReportHandler handler : handlers) {
            handler.entering(contextName, methodName, args);
        }
    }

    public static void exiting() {
        exiting(null, null);
    }

    public static void exiting(String contextName, String methodName) {
        for (ReportHandler handler : handlers) {
            handler.exiting(contextName, methodName);
        }
    }

    public static void reportMessage(String message) {
        for (ReportHandler handler : handlers) {
            handler.reportMessage(message);
        }
    }

    public static void reportFatal(String message) {
        for (ReportHandler handler : handlers) {
            handler.reportFatal(message);
        }
    }

    public static void reportError(String message) {
        for (ReportHandler handler : handlers) {
            handler.reportError(message);
        }
    }

    public static void reportInfo(String message) {
        for (ReportHandler handler : handlers) {
            handler.reportInfo(message);
        }
    }

    public static void reportWarning(String message) {
        for (ReportHandler handler : handlers) {
            handler.reportWarning(message);
        }
    }

    public static void reportVerification(String message, boolean result, Throwable exception) {
        for (ReportHandler handler : handlers) {
            handler.reportVerification(message, result, exception);
        }
    }

    public static void reportAssertion(String message, boolean result, Throwable exception) {
        for (ReportHandler handler : handlers) {
            handler.reportAssertion(message, result, exception);
        }
    }

    public static void reportException(Throwable t) {
        for (ReportHandler handler : handlers) {
            handler.reportException(t);
        }
    }

    public static void reportFileReference(String filename) {
        for (ReportHandler handler : handlers) {
            handler.reportFileReference(filename);
        }
    }

    public static void reportScreenshot(byte[] bytes) {
        for (ReportHandler handler : handlers) {
            handler.reportScreenshot(bytes);
        }
    }
}
