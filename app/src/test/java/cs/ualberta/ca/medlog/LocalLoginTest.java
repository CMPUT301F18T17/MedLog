package cs.ualberta.ca.medlog;

import android.content.Context;

import junit.framework.TestCase;
import org.junit.Test;

import cs.ualberta.ca.medlog.helper.LocalLogin;

import static androidx.test.InstrumentationRegistry.getContext;

/**
 *
 * <h1>
 *     LocalCacheTest
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         Unit testing for the LocalCache class
 *
 * </p>
 *
 * @author Tem Tamre
 * @contact ttamre@ualberta.ca
 * @see cs.ualberta.ca.medlog.helper.LocalCache
 */

public class LocalLoginTest extends TestCase {

    @Test
    public void testCreation() {
        LocalLogin loginObjectA = new LocalLogin(getContext());
        assertNotNull(loginObjectA);

        LocalLogin loginObjectB = new LocalLogin(getContext(), "test.txt");
        assertNotNull(loginObjectB);
    }


    @Test
    public void testSaveUserLogin() {
        // Not sure how to test this
    }

    @Test
    public void testCheckUserLogin() {
        LocalLogin login = new LocalLogin(getContext());
        String[] active = {"UserA", "UserB", "UserC"};

        for (String username: active) {
            assertTrue(login.checkUserLogin(username));
        }
    }
}
