package de.telekom.testframework.selenium;

import de.telekom.testframework.reporting.Reporter;
import de.telekom.testframework.selenium.annotations.Path;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;

/**
 *
 * @author Daniel
 */
public abstract class Page extends Mask {

    protected final Application application;

    public Page(Application application) {
        super(application.getWebDriver());
        Objects.requireNonNull(application, "application is null");

        this.application = application;

    }

    @Override
    public String toString() {
        return super.toString() + "[ path = '" + getPath() + "']";
    }

    public boolean isCurrentPage() {

        ActionHandler.waitUntilPageLoaded(webDriver, this);

        String u = getWebDriver().getCurrentUrl();

        URL url;
        try {
            url = new URL(u);

            return url.getPath().startsWith(getPath());
        } catch (MalformedURLException ex) {
        }
        return false;

    }

    public void navigateTo() {
        navigateTo("");
    }

    public void navigateTo(final String path) {
        Objects.requireNonNull(path, "path must be non null");

        handle("navigateTo", new Runnable() {
            @Override
            public void run() {
                application.navigateTo(getPath() + path);
            }
        });
    }

    public String getPath() {
        Path path = this.getClass().getAnnotation(Path.class);
        if (path != null) {
            return path.value();
        }

        return "";
    }

    public void reportScreenShot() {
        Reporter.reportScreenshot(ActionHandler.getScreenshotAs(this, OutputType.BYTES));
    }

    public boolean isLoaded() {
        return ((JavascriptExecutor) getWebDriver()).executeScript("return document.readyState").equals("complete");
    }
}
