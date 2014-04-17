package de.telekom.testframework.testng;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.telekom.testframework.VerificationError;
import de.telekom.testframework.annotations.NoVerificationErrors;
import de.telekom.testframework.reporting.ReportHandler;
import de.telekom.testframework.selenium.annotations.ResetWebDriver;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.guice.WebDriverModule;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;

/**
 *
 * @author Daniel
 */
public class TestNGTestListener implements IExecutionListener, IInvokedMethodListener2, ITestListener, IAnnotationTransformer {

    public TestNGTestListener() {

    }

    static ReportHandler reportHandler = new TestNGReportHandler();

    @Override
    public void onExecutionStart() {
        de.telekom.testframework.reporting.Reporter.AddHandler(reportHandler);
    }

    @Override
    public void onExecutionFinish() {

    }

    List<Object> injectedInstances = new ArrayList<>();

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        Reporter.setCurrentTestResult(testResult);

        de.telekom.testframework.reporting.Reporter.startTest(testResult.getInstanceName() + '.' + testResult.getName());

        Object instance = testResult.getMethod().getInstance();
        if (instance.getClass().isAnnotationPresent(UseWebDriver.class)) {

            AccessibleObject ac = method.getTestMethod().getConstructorOrMethod().getMethod();
            if (ac == null) {
                ac = method.getTestMethod().getConstructorOrMethod().getConstructor();
            }

            WebDriver webDriver = (WebDriver) context.getAttribute("webDriver");

            // if the instance is not injected or the webdriver is'nt started or the testmethod needs a restart of the webdriver
            // (re)start the the webdriver and inject the members
            if (!injectedInstances.contains(instance) || webDriver == null || ac.isAnnotationPresent(ResetWebDriver.class)) {

                if (webDriver != null) {
                    try {
                        injectedInstances.clear();
                        context.setAttribute("webDriver", null);
                        webDriver.quit();
                    } catch (Throwable e) {

                    }
                }

                WebDriverModule module = new WebDriverModule();

                Injector injector = Guice.createInjector(module);
                injector.injectMembers(instance);
                injectedInstances.add(instance);

                context.setAttribute("webDriver", module.getDriver());
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        Reporter.setCurrentTestResult(testResult);

        AccessibleObject ac = method.getTestMethod().getConstructorOrMethod().getMethod();
        if (ac == null) {
            ac = method.getTestMethod().getConstructorOrMethod().getConstructor();
        }

        if (testResult.getStatus() != ITestResult.FAILURE) {

            Object a = testResult.getAttribute(TestNGReportHandler.VERIFICATION_ATTRIBUTE);
            if (a != null && !expectVerificationErrors.contains(ac) && !ac.isAnnotationPresent(NoVerificationErrors.class)) {

                @SuppressWarnings("unchecked")
                List<Throwable> o = (List<Throwable>) a;

                if (!o.isEmpty()) {
                    String s = "";
                    for (Throwable v : o) {
                        if (!s.isEmpty()) {
                            s += "\n";
                        }
                        s += "    " + v.getLocalizedMessage();
                    }

                    VerificationError ex = new VerificationError("Failed verifications found:\n" + s);
                    ex.setStackTrace(o.get(0).getStackTrace());

                    testResult.setStatus(ITestResult.FAILURE);
                    testResult.setThrowable(ex);
                }
            }
        }

        de.telekom.testframework.reporting.Reporter.endTest(testResult.getInstanceName() + '.' + testResult.getName());
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        WebDriver webDriver = (WebDriver) context.getAttribute("webDriver");
        if (webDriver != null) {
            try {
                context.setAttribute("webDriver", null);
                injectedInstances.clear();
                webDriver.quit();
            } catch (Throwable e) {

            }
        }
    }

    List<AnnotatedElement> expectVerificationErrors = new ArrayList<>();

    @Override
    @SuppressWarnings({"rawtypes", "unchecked", "unchecked"})
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // WebDriver tests are always single threaded

        if ((testClass != null && testClass.isAnnotationPresent(UseWebDriver.class))
                || (testConstructor != null && testConstructor.getDeclaringClass().isAnnotationPresent(UseWebDriver.class))
                || (testMethod != null && testMethod.getDeclaringClass().isAnnotationPresent(UseWebDriver.class))) {
            annotation.setSingleThreaded(true);
        }

        // Remove the VerificationErrors from the expected exception list,
        // because these errros where throwed after the TestNG check for the 
        // expected exceptions
        boolean b = false;
        List<Class<?>> expectedExceptions = new ArrayList<>();
        for (Class<?> e : annotation.getExpectedExceptions()) {
            if (e == VerificationError.class) {
                b = true;
            } else {
                expectedExceptions.add(e);
            }
        }

        if (b) {
            if (testClass != null) {
                expectVerificationErrors.add(testClass);
            }
            if (testMethod != null) {
                expectVerificationErrors.add(testMethod);
            }
            if (testConstructor != null) {
                expectVerificationErrors.add(testConstructor);
            }
            annotation.setExpectedExceptions(expectedExceptions.toArray(new Class<?>[0]));
        }

    }

}
