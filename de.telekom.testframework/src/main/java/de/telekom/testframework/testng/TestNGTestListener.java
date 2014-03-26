package de.telekom.testframework.testng;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.telekom.testframework.VerificationError;
import de.telekom.testframework.reporting.ReportHandler;
import de.telekom.testframework.selenium.annotations.ResetWebDriver;
import de.telekom.testframework.selenium.annotations.UseWebDriver;
import de.telekom.testframework.selenium.guice.WebDriverModule;
import java.lang.reflect.AccessibleObject;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 *
 * @author Daniel
 */
public class TestNGTestListener implements IExecutionListener, IInvokedMethodListener2, ITestListener {

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

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        Reporter.setCurrentTestResult(testResult);

        de.telekom.testframework.reporting.Reporter.startTest(testResult.getName());

        Object o = testResult.getMethod().getInstance();
        if (o.getClass().isAnnotationPresent(UseWebDriver.class)) {

            AccessibleObject ac = method.getTestMethod().getConstructorOrMethod().getMethod();
            if (ac == null) {
                ac = method.getTestMethod().getConstructorOrMethod().getConstructor();
            }

            WebDriver webDriver = (WebDriver) context.getAttribute("webDriver");

            if (webDriver == null || ac.isAnnotationPresent(ResetWebDriver.class)) {

                if (webDriver != null) {
                    webDriver.quit();
                }

                WebDriverModule module = new WebDriverModule();

                Injector injector = Guice.createInjector(module);
                injector.injectMembers(o);

                context.setAttribute("webDriver", module.getDriver());
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        Reporter.setCurrentTestResult(testResult);

        if (method.isTestMethod() && testResult.getStatus() != ITestResult.FAILURE) {

            Object a = testResult.getAttribute(TestNGReportHandler.VERIFICATION_ATTRIBUTE);
            if (a != null) {
                
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
        de.telekom.testframework.reporting.Reporter.endTest(testResult.getName());
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
            webDriver.quit();
        }
    }

}
