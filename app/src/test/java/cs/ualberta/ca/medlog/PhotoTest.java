/*
 * Test class for the Photo entity
 *
 * Authors: Thomas Roskewich, Tem Tamre
 * Contact: roskewic@ualberta.ca, ttamre@ualberta.ca
 * Created: October 30, 2018
 */

package cs.ualberta.ca.medlog;

import cs.ualberta.ca.medlog.entity.Photo;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PhotoTest {

    @Test
    public void testGetIdentifier() {

        // With a positive identifier
        Photo p1 = new Photo(100, null);
        assertEquals(p1.getIdentifier(), 100);

        // With a negative identifier
        Photo p2;

        try {
            p2 = new Photo(-100, null);
        } catch(RuntimeException e) {
            p2 = null;
        }
        assertNull(p2);
    }

    @Test
    public void testGetPhotoBitmap() {
        Photo p = new Photo(1000, null);
        assertNull(p.getPhotoBitmap());
    }
}
