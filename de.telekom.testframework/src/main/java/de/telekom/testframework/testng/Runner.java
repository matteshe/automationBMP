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
//    public static void main(String[] args) throws ClassNotFoundException, IOException {
//        Properties properties = new Properties();
//
//        properties.load(Runner.class.getResourceAsStream("/qcids.properties"));
//
//        List<XmlSuite> suites = new ArrayList<>();
//       
//        for (int i = 0; i < args.length; i++) {
//            if ("-qcid".equals(args[i])) {
//                i++;
//                if (i < args.length) {
//                    String[] ids = args[i].split(",");
//                    for (String id : ids) {
//
//                        String c = properties.getProperty(id);
//                        if (c == null) {
//                            throw new ClassNotFoundException("cannot find a class for QualityCenter Id " + id);
//                        }
//                        XmlSuite suite = new XmlSuite();
//                        suite.setName(id);
//
//                        XmlTest test = new XmlTest(suite);
//                        test.setName(c);
//                        List<XmlClass> classes = new ArrayList<>();
//                        classes.add(new XmlClass(c));
//                        test.setXmlClasses(classes);
//
//                        System.out.println(suite.toXml());
//                        suites.add(suite);
//                    }
//                }
//            }
//        }
//
//        
//        TestNG result = new TestNG();
//
//        result.setXmlSuites(suites);
//        
//        result.run();
//    }
}
