package de.telekom.testframework.configuration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nnsoft.guice.rocoto.Rocoto;
import org.nnsoft.guice.rocoto.configuration.ConfigurationModule;

/**
 *
 * @author Daniel
 */
public class Configuration {

    private static void saveDefaultProperties(Object instance, String confResourcePath) {
        Properties props = new Properties();
        for (Field f : instance.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Named.class)) {
                Named n = f.getAnnotation(Named.class);
                Object v = null;
                try {
                    f.setAccessible(true);
                    v = f.get(instance);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                }
                props.setProperty(n.value(), v != null ? v.toString() : "");
            }
            if (f.isAnnotationPresent(javax.inject.Named.class)) {
                javax.inject.Named n = f.getAnnotation(javax.inject.Named.class);
                Object v = null;
                try {
                    f.setAccessible(true);
                    v = f.get(instance);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                }
                props.setProperty(n.value(), v != null ? v.toString() : "");
            }
        }
        File f = new File(confResourcePath);
        File d = f.getParentFile();
        d.mkdirs();
        
        try (FileOutputStream output = new FileOutputStream(confResourcePath)) {
            props.store(output, "default values");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        };
    }

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

        URL confRes = null;
        String confResourcePath = "conf/" + cls.getName() + ".properties";
        if (confResourcePath != null && !confResourcePath.isEmpty()) {
            confRes = createUrlOrFileUrl(confResourcePath);
        }
        final URL confResource = confRes;

        if (confResource == null) {
            if (System.getProperties().containsKey("config.savedefaults")) {
                saveDefaultProperties(instance, confResourcePath);
            }
        }

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

                if (confResource != null) {
                    bindProperties(confResource);
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
