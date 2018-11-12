package cs.ualberta.ca.medlog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import cs.ualberta.ca.medlog.helper.Database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class DatabaseUnitTest {

    /**
     * Ensure that the checkConnectivity is properly returning true or false
     * when the server is online/offline
     *
     * NOTE: Manually check both Elastisearch databases to make sure that online is online and
     * that offline is offline
     */
    @Test
    public void testCheckConnectivity() {

        Database database = new Database(null);
        database.setTimeout(1000);
        String offline = "http://cmput300.softwareprocess.es:8080/cmput301f18t17";

        /* Successfully to an online database */
        database.checkConnectivity();

        /* Unsuccessfully connecting to an offline database */
        assertFalse(database.checkConnectivity(offline));
    }

    /**
     * <p>Ensure that the context of the database can be retrieved properly</p>
     */
    @Test
    public void testGetDatabaseContext() {
        Database database = new Database(null);
        assertNull(database.getDatabaseContext());
    }
    /**
     * <p>Ensure that the timeout duration of the database can be retrieved properly</p>
     */
    @Test
    public void testGetTimeout() {
        Database database = new Database(null);
        assertEquals(10, database.getTimeout());
    }


    /* Tests for setter methods */

    /**
     * Ensure that the context of the database can be set properly
     */
    @Test
    public void testSetDatabaseContext() {
        // Pretty sure this can be tested through an instrumented test
        // Not sure how to unit test this though
    }

    /**
     * Ensure that the timeout duration of the database can be set properly
     */
    @Test
    public void testSetTimeout() {
        Database database = new Database(null);
        assertEquals(10, database.getTimeout());

        database.setTimeout(3);
        assertEquals(3, database.getTimeout());

        database.setTimeout(5);
        assertEquals(5, database.getTimeout());
    }

}
