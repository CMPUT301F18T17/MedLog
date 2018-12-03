package cs.ualberta.ca.medlog;

import junit.framework.TestCase;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Cache;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         Unit testing for the Cache class
 * </p>
 * <p>
 *     Issues: <br>
 *         Without context, some tests will always fail.
 * </p>
 *
 * @author Tem Tamre, Calvin Chomyc
 * @version 1.1
 * @see Cache
 */
public class CacheTest extends TestCase {

    /**
     * Tests if a Cache object is successfully created.
     */
    @Test
    public void testCreation() {
        Cache cache = new Cache(InstrumentationRegistry.getInstrumentation().getTargetContext());
        assertNotNull(cache);
    }

    /**
     * Tests if a patient can be added and retrieved from the cache.
     */
    @Test
    public void testLoadPatient() {
        ContactInfo info = new ContactInfo("0000000000", "email@email.ca");
        Patient patient = new Patient(info, "Test Patient");
        AppStatus.getInstance().setCurrentUser(patient);
        Cache cache = new Cache(InstrumentationRegistry.getInstrumentation().getTargetContext());
        cache.save();
        try {
            assertEquals(patient, cache.load(Patient.class));
        }
        catch (UserNotFoundException e) {
            fail();
        }
    }

    /**
     * Tests if a provider can be added and retrieved from the cache.
     */
    @Test
    public void testLoadProvider(){
        CareProvider provider = new CareProvider("Test Provider");
        AppStatus.getInstance().setCurrentUser(provider);
        Cache cache = new Cache(InstrumentationRegistry.getInstrumentation().getTargetContext());
        cache.save();
        try {
            assertEquals(provider, cache.load(CareProvider.class));
        }
        catch (UserNotFoundException e) {
            fail();
        }
    }

    /**
     * Tests if an exception is thrown when no users are added to the cache and load is called.
     */
    @Test
    public void testInvalidLoad() {
        Cache cache = new Cache(InstrumentationRegistry.getInstrumentation().getTargetContext());
        try {
            cache.load(Patient.class);
        }
        catch (UserNotFoundException e) {
            fail();
        }
    }
}