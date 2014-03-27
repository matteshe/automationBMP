package de.telekom.testframework.testng;

import de.telekom.testframework.VerificationError;
import de.telekom.testframework.reporting.ReportHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.testng.Reporter;
import org.testng.util.Strings;

/**
 * The Report Handler for ReportNG
 * 
 * @author Daniel Biehl
 */
class TestNGReportHandler implements ReportHandler {

    static {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        System.setProperty("org.uncommons.reportng.stylesheet", "dtreportng.css");
    }

    public TestNGReportHandler() {
    }

    public void log(String style, String message, boolean escape) {
        if (message == null) {
            message = "";
        }

        Reporter.log("<p class='line " + (style != null && !style.isEmpty() ? style : "") + "'>");
        Reporter.log(escape ? Strings.escapeHtml(message) : message);
        Reporter.log("</p>");
    }

    public void log(String style, String message) {
        log(style, message, true);
    }

    public void log(String message) {
        log(null, message);
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

        log("entering", o);

        Reporter.log("<div class='block'>");
    }

    @Override
    public void exiting(String contextName, String actionName) {
        Reporter.log("</div>");
        //log("exiting", contextName + "." + actionName);
    }

    @Override
    public void reportMessage(String message) {
        log(message);
    }

    @Override
    public void reportFatal(String message) {
        log("fatal", "FATAL: " + message);
    }

    @Override
    public void reportError(String message) {
        log("error", "ERROR: " + message);
    }

    @Override
    public void reportInfo(String message) {
        log("info", "INFO: " + message);
    }

    @Override
    public void reportWarning(String message) {
        log("warning", "WARNING: " + message);
    }

    public static final String VERIFICATION_ATTRIBUTE = "verification";

    @Override
    public void reportVerification(String message, boolean result, Throwable exception) {

        if (message == null) {
            message = "";
        }

        String s = Strings.escapeHtml(message);

        log("verification" + (result ? " passed" : " failed"), (result ? "PASSED" : " FAILED") + ": " + s, false);

        if (!result) {
            Throwable ex = exception;
            if (ex == null) {
                ex = new VerificationError(s);
            }

            @SuppressWarnings("unchecked")
            List<Throwable> o = (List<Throwable>) Reporter.getCurrentTestResult().getAttribute(VERIFICATION_ATTRIBUTE);
            if (o == null) {
                o = new ArrayList<>();
            }
            o.add(ex);
            Reporter.getCurrentTestResult().setAttribute(VERIFICATION_ATTRIBUTE, o);
        }
    }

    public static final String ASSERTION_ATTRIBUTE = "assertion";

    @Override
    public void reportAssertion(String message, boolean result, Throwable exception) {

        if (message == null) {
            message = "";
        }

        String s = Strings.escapeHtml(message);

        log("assertion" + (result ? " passed" : " failed"), (result ? "PASSED" : " FAILED") + ": " + s, false);

        if (!result) {
            Throwable ex = exception;
            if (ex == null) {
                ex = new VerificationError(s);
            }

            @SuppressWarnings("unchecked")
            List<Throwable> o = (List<Throwable>) Reporter.getCurrentTestResult().getAttribute(ASSERTION_ATTRIBUTE);
            if (o == null) {
                o = new ArrayList<>();
            }
            o.add(ex);
            Reporter.getCurrentTestResult().setAttribute(ASSERTION_ATTRIBUTE, o);
        }
    }

    int id = 0;

    @Override
    public void reportException(Throwable t) {
        String s = buildExceptionLog(t);

        log("exception", s, false);
    }

    protected String buildExceptionLog(Throwable t) {
        id++;
        String s = "EXCEPTION: <a class=\"exception\" href=\"javascript:toggleElement('throwable-" + id + "', 'block')\" title=\"Click to expand/collapse\">" + Strings.escapeHtml(t.toString()) + "\n";
        s += "</a>\n";
        s += "<div class='stackTrace' id='throwable-" + id + "'>\n";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        s += sw.toString().replaceAll("\\r?\\n", "<br/>" + System.getProperty("line.separator"));
        s += "</div>\n";

        return s;
    }

    @Override
    public void reportFileReference(String filename) {

        File file = new File(filename);

        String newFilename = copyReference(filename, file);

        log("reference", "REFERENCE: <a href='" + newFilename + "'>" + filename + "</a>", false);
    }

    protected File getReferencesDir() {
        File outputDir = new File(Reporter.getCurrentTestResult().getTestContext().getOutputDirectory()).getParentFile();

        File referencesDir = new File(outputDir, "references");
        if (!referencesDir.exists()) {
            referencesDir.mkdirs();
        }
        return referencesDir;
    }

    protected String copyReference(String filename, File file) {
        String newFilename = FilenameUtils.getBaseName(filename) + "_" + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())) + "." + FilenameUtils.getExtension(filename);
        if (file.exists()) {

            try {
                FileUtils.copyFile(file, new File(getReferencesDir(), newFilename));
            } catch (IOException ex) {
                reportWarning(ex.toString());
            }

        } else {
            reportWarning("file " + filename + " not exists");
        }
        return "../references/" + newFilename;
    }

    @Override
    public void reportScreenshot(byte[] bytes) {
        if (bytes == null) {
            return;
        }

        String newFilename = "Screenshot" + "_" + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())) + ".png";

        File sc = new File(getReferencesDir(), newFilename);
        try (FileOutputStream out = new FileOutputStream(sc)) {
            out.write(bytes);
        } catch (FileNotFoundException ex) {
            reportWarning(ex.toString());
        } catch (IOException ex) {
            reportWarning(ex.toString());
        }
        newFilename = "../references/" + newFilename;

        log("screenshotcontainer", "SCREENSHOT: <a href='" + newFilename + "'><img class='screenshot' src='" + newFilename + "'/></a>", false);
    }

    @Override
    public void startTest(String name) {

    }

    @Override
    public void endTest(String name) {

    }
}
