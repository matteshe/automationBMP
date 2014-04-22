package de.telekom.testframework.annotations.processors;

import de.telekom.testframework.annotations.QCId;
import de.telekom.testframework.annotations.QCState;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 *
 * @author Daniel
 */
@SupportedAnnotationTypes({"de.telekom.testframework.annotations.QCId"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class QCIdAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.errorRaised()) {
            return false;
        }

        if (!roundEnv.processingOver()) {
            Properties p = new Properties();
            int count = 0;
            int readyCount = 0;
            int onGoingCount = 0;
            int readyButNeedsReviewCount = 0;
            for (Element element : roundEnv.getElementsAnnotatedWith(QCId.class)) {

                QCId annotation = element.getAnnotation(QCId.class);
                if (annotation == null) {
                    continue;
                }

                String old = p.getProperty(annotation.value(), null);
                if (old != null) {
                    processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.MANDATORY_WARNING,
                            "QCId annotation " + annotation.value() + " from class " + element.toString()
                            + " overrides the QCId annotation from class " + old,
                            element);
                }
                p.setProperty(annotation.value(), element.toString());
                //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "found qcid " + annotation.value() + " => " + element.toString());
                count++;
                if (annotation.state() == QCState.Ready) {
                    readyCount++;
                }

                if (annotation.state() == QCState.ReadyButNeedsReview) {
                    readyButNeedsReviewCount++;
                }
                if (annotation.state() == QCState.Ongoing) {
                    onGoingCount++;
                }
            }

            try {

                FileObject f = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "qcids.properties");

                try (OutputStream os = f.openOutputStream()) {
                    p.store(os, "quality center ids for testng");
                    os.flush();
                }
            } catch (IOException ex) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.getLocalizedMessage());
            }

            // TODO remove the following System.out.println's
            // TODO find a way that maven shows the getMessenger().printMessage(Kind.Note....
            if (count > 0) {
                //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "found " + count + " QCId's");
                System.out.println("found " + count + " QCId's");
            }
            if (readyCount > 0) {
                //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "found " + readyCount + " ready QCId's");
                System.out.println("found " + readyCount + " ready QCId's");
            }
            if (readyButNeedsReviewCount > 0) {
                //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "found " + readyButNeedsReviewCount + " ready but need review QCId's");
                System.out.println("found " + readyButNeedsReviewCount + " ready but need review QCId's");
            }

            if (onGoingCount > 0) {
                //processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "found " + onGoingCount + " on going QCId's");
                System.out.println("found " + onGoingCount + " on going QCId's");
            }

        }

        return true;
    }

}
