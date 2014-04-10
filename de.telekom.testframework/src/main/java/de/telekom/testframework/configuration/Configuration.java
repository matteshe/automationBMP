package de.telekom.testframework.configuration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import org.nnsoft.guice.rocoto.Rocoto;
import org.nnsoft.guice.rocoto.configuration.ConfigurationModule;

/**
 *
 * @author Daniel
 */
public class Configuration {

    public Configuration() {
    }

    public final void initialize() {
        initialize(this);
    }

    private static URL createUrlOrFileUrl(String pathOrUrl) {
        try {
            return new URL(pathOrUrl);
        } catch (MalformedURLException ex) {
        }

        try {
            File f = new File(pathOrUrl);
            if (f.exists()) {
                return f.toURI().toURL();
            }
        } catch (MalformedURLException ex) {
        }
        return null;
    }

    public static final void initialize(Object instance) {

        Class<?> cls = instance.getClass();
        if (cls.isMemberClass()) {
            cls = cls.getDeclaringClass();
        }

        if (cls.isAnonymousClass()) {
            cls = cls.getEnclosingClass();
        }

        String n = cls.getSimpleName() + ".properties";

        final URL classResource = cls.getResource(n);

        URL systemRes = null;
        String systemResourcePath = System.getProperty(cls.getName() + ".properties", null);
        if (systemResourcePath != null && !systemResourcePath.isEmpty()) {
            systemRes = createUrlOrFileUrl(systemResourcePath);
        }
        final URL systemResource = systemRes;

        final Injector injector = Guice.createInjector(Rocoto.expandVariables(new ConfigurationModule() {

            @Override
            protected void bindConfigurations() {

                if (classResource != null) {
                    bindProperties(classResource);
                }

                if (systemResource != null) {
                    bindProperties(systemResource);
                }

                bindEnvironmentVariables();

                bindSystemProperties();
            }
        }));
        
        injector.injectMembers(instance);
        
    }

    public static <T> T create(Class<T> aClass) {
        try {

            Constructor<?> constructor = null;
            for (Constructor<?> cons : aClass.getDeclaredConstructors()) {
                if (cons.getTypeParameters().length == 0) {
                    constructor = cons;
                }
            }
            if (constructor == null) {
                constructor = aClass.getConstructor();
            }

            @SuppressWarnings("unchecked")
            T instance = (T) constructor.newInstance();

            initialize(instance);
            return instance;
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException("cannot find a valid constructor for class " + aClass.getName(), ex);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("cannot instanciate class " + aClass.getName(), ex);
        }

    }

}
