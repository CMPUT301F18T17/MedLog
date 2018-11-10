/**
 *
 * <h1>
 *     Test class for the Elastisearch database
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         Unit testing for the Database class
 *
 * </p>
 *
 * @author Tem Tamre
 * @contact ttamre@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.Database
 */

package cs.ualberta.ca.medlog;

import junit.framework.TestCase;
import org.junit.Test;
import cs.ualberta.ca.medlog.helper.Database;


public class DatabaseTest extends TestCase {

    /* Tests for getter methods */

    /**
     * <p>Ensure that the context of the database can be retrieved properly</p>
     */
    @Test
    public void testGetDatabaseContext() {
        Database database = new Database();
        assertNull(database.getDatabaseContext());
    }

    /**
     * <p>Ensure that the address of the database can be retrieved properly</p>
     */
    @Test
    public void testGetDatabaseAddress() {
        Database database = new Database();
        assertNull(database.getDatabaseAddress());
    }

    /**
     * <p>Ensure that the timeout duration of the database can be retrieved properly</p>
     */
    @Test
    public void testGetTimeout() {
        Database database = new Database();
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
     * Ensure that the address of the database can be set properly
     */
    @Test
    public void testSetDatabaseAddress() {
        String addrA = "http://cmput301.softwareprocess.es:8080/cmput301f18t17";
        String addrB = "http://cmput300.softwareprocess.es:8080/cmput301f18t17";

        Database database = new Database();
        assertNull(database.getDatabaseAddress());

        database.setDatabaseAddress(addrA);
        assertEquals(addrA, database.getDatabaseAddress());

        database.setDatabaseAddress(addrB);
        assertEquals(addrB, database.getDatabaseAddress());
    }

    /**
     * Ensure that the timeout duration of the database can be set properly
     */
    @Test
    public void testSetTimeout() {
        Database database = new Database();
        assertEquals(10, database.getTimeout());

        database.setTimeout(3);
        assertEquals(3, database.getTimeout());

        database.setTimeout(5);
        assertEquals(5, database.getTimeout());
    }


    /* Tests for loading/GET methods */

    @Test
    public void testLoadPatientLocal() {}

    @Test
    public void testLoadPatientRemote() {}

    @Test
    public void testLoadProviderLocal() {}

    @Test
    public void testLoadProviderRemote() {}


    /* Tests for saving/PUT methods */

    @Test
    public void testSavePatientLocal() {}

    @Test
    public void testSavePatientRemote() {}

    @Test
    public void testSaveProviderLocal() {}

    @Test
    public void testSaveProviderRemote() {}


    /* Tests for other methods */

    @Test
    public void testSearchPatients() {}

    @Test
    public void testSearchProviders() {}

    /**
     * Ensure that the checkConnectivity is properly returning true or false
     * when the server is online/offline
     *
     * NOTE: Manually check both Elastisearch databases to make sure that online is online and
     * that offline is offline
     */
    @Test
    public void testCheckConnectivity() {
        Database database = new Database();
        String online = "http://cmput301.softwareprocess.es:8080/cmput301f18t17";
        String offline = "http://cmput300.softwareprocess.es:8080/cmput301f18t17";

        /* Successfully to an online database */
        database.setDatabaseAddress(online);
        assertEquals(online, database.getDatabaseAddress());
        assertTrue(database.checkConnectivity());

        /* Unsuccessfully connecting to an offline database */
        database.setDatabaseAddress(offline);
        assertEquals(offline, database.getDatabaseAddress());
        assertFalse(database.checkConnectivity());
    }
}
