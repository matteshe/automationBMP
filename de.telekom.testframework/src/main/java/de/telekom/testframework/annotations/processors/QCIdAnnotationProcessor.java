package de.telekom.testframework.annotations.processors;

import de.telekom.testframework.annotations.QCId;
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

            for (Element element : roundEnv.getElementsAnnotatedWith(QCId.class)) {
                QCId annotation = element.getAnnotation(QCId.class);

                p.setProperty(annotation.value(), element.toString());
                System.out.println("found qcid " + annotation.value() + " => " + element.toString());
            }

            try {

                FileObject f = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "qcids.properties");

                OutputStream os = f.openOutputStream();
                try {
                    p.store(os, "quality center ids for testng");
                    os.flush();
                } finally {
                    os.close();
                }
            } catch (IOException ex) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.getLocalizedMessage());
            }
            
        }
        
        return true;
    }

}
