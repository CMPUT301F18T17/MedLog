package cs.ualberta.ca.medlog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.helper.ElasticSearchController;

import static junit.framework.TestCase.assertTrue;
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
        String offline = "tes.d";
        Database db = new Database(null);
        /* Successfully to an online database */
        assertTrue(ElasticSearchController.checkConnectivity("http://cmput301.softwareprocess.es:8080/"));

        /* Unsuccessfully connecting to an offline database */
        assertFalse(ElasticSearchController.checkConnectivity(offline));
    }

    /**
     * <p>Ensure that the context of the database can be retrieved properly</p>
     */
    @Test
    public void testGetDatabaseContext() {
        Database database = new Database(null);
        assertNull(database.getDatabaseContext());
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


}
