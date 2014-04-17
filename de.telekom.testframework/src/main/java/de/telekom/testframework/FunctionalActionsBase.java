package de.telekom.testframework;

import de.telekom.testframework.reporting.Reporter;

/**
 *
 * @author Daniel
 */
public abstract class FunctionalActionsBase {

    public static void handle(String actionName, final Runnable r, Object... args) {
        handle(actionName, new RunnableFunction<Integer>() {

            @Override
            public Integer run() {
                r.run();
                return 0;
            }
        }, args);
    }

    public static <T> T handle(String actionName, RunnableFunction<T> r, Object... args) {
        Reporter.entering(null, actionName, args);

        try {
            return r.run();

        } catch (Throwable e) {
            Reporter.reportException(e);

            throw e;
        } finally {
            Reporter.exiting(null, actionName);
        }
    }
}
