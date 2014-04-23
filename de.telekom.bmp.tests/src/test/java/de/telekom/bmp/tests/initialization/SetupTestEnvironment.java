package de.telekom.bmp.tests.initialization;

import de.telekom.bmp.data.Application;
import de.telekom.bmp.data.Datapool;
import javax.inject.Inject;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@Guice
public class SetupTestEnvironment {

    @Inject
    Datapool datapool;

    @Test
    public void dropDataStore() {
        datapool.dropDatastore();
    }

    @Test(dependsOnMethods = "dropDataStore")
    public void defineTestApplications() {
        // TODO define test applications

        Application app = new Application();
        app.name = "Create Free Trial";
        datapool.save(app);
    }

}
