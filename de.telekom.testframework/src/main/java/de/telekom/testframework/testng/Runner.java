package de.telekom.testframework.testng;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.TestNG;

/**
 * A TestNG Runner that wraps the TestNG runner and translates the "-qcid" parameter to 
 * the corespondig TestNG test class
 * 
 * @author Daniel Biehl
 */
public class Runner {

    public static void main(String[] args) throws ClassNotFoundException {
        Properties properties = new Properties();

        try {
            InputStream is = Runner.class.getResourceAsStream("/qcids.properties");
            if (is != null) {
                properties.load(is);

            }
        } catch (IOException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.INFO, null, ex);
        }

        String clz = null;

        List<String> a = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if ("-qcid".equals(args[i])) {
                i++;
                if (i < args.length) {
                    String[] ids = args[i].split(",");
                    for (String id : ids) {
                        if (clz != null) {
                            clz += ",";
                        }
                        String c = properties.getProperty(id);
                        if (c == null) {
                            throw new ClassNotFoundException("cannot find a class for QualityCenter Id " + id);
                        }
                        if (clz == null) {
                            clz = c;
                        } else {
                            clz += c;
                        }
                    }
                }
            } else {
                a.add(args[i]);
            }
        }

        if (clz != null) {
            a.add("-testclass");
            a.add(clz);
        }

        TestNG.main(a.toArray(new String[0]));
    }

}
