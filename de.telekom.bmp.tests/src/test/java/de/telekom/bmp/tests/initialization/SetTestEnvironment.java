package de.telekom.bmp.tests.initialization;

import de.telekom.bmp.data.Datapool;
import javax.inject.Inject;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

/**
 *
 * @author Daniel
 */
@Guice
public class SetTestEnvironment {

    @Inject
    Datapool datapool;

    @Test
    public void dropDataStore() {
        datapool.dropDatastore();
    }

}
