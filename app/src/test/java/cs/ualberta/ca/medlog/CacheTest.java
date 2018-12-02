/**
 *
 * <h1>
 *     CacheTest
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         Unit testing for the Cache class
 *
 * </p>
 *
 * @author Tem Tamre
 * @contact ttamre@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.Cache
 */

package cs.ualberta.ca.medlog;

import junit.framework.TestCase;
import org.junit.Test;

import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Cache;
import cs.ualberta.ca.medlog.singleton.AppStatus;

public class CacheTest extends TestCase {

    @Test
    public void testCreation() {
        Cache cache = new Cache(null);
        assertNotNull(cache);
    }


    /* Tests for loading methods */

    @Test
    public void testLoadPatient() {
        /*
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        AppStatus.getInstance().setCurrentUser(patient);
        Cache cache = new Cache(null); // I think we need an actual context here
        cache.save(); // We have separate tests for saving, but I think we need something saved to test loading.
        try {
            assertEquals(patient, cache.load(Patient.class));
        }
        catch (UserNotFoundException e) {
            fail();
        }
        */
    }

    @Test
    public void testLoadProvider() {
        /*
        CareProvider provider = new CareProvider("Test Provider");
        AppStatus.getInstance().setCurrentUser(provider);
        Cache cache = new Cache(null); // I think we need an actual context here
        cache.save(); // We have separate tests for saving, but I think we need something saved to test loading.
        try {
            assertEquals(provider, cache.load(CareProvider.class));
        }
        catch (UserNotFoundException e) {
            fail();
        }
        */
    }

    @Test
    public void testInvalidLoad() {
        /*
        Cache cache = new Cache(null); // I think we need an actual context here
        try {
            cache.load(Patient.class);
            fail();
        }
        catch (UserNotFoundException e) {}
        */
    }


    /* Tests for saving methods */

    @Test
    public void testSavePatient() {
        /*
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        AppStatus.getInstance().setCurrentUser(patient);
        Cache cache = new Cache(null); // I think we need an actual context here
        cache.save();
        */
    }

    @Test
    public void testSaveProvider() {
        /*
        CareProvider provider = new CareProvider("Test Provider");
        AppStatus.getInstance().setCurrentUser(provider);
        Cache cache = new Cache(null); // I think we need an actual context here
        cache.save();
        */
    }
}
