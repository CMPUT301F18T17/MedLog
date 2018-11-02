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
    public void testPhoto(){
        Photo p = new Photo(34, null);
        assertEquals(p.getIdentifier(), 34);
    }

    @Test
    public void testNegId(){
        Photo p;

        try {
            p = new Photo(-1, null);
        } catch(RuntimeException e) {
            p = null;
        }

        assertNull(p);
    }

    @Test
    public void testGetPhotoBitmap() {
        Photo p = new Photo(1000, null);
        assertNull(p.getPhotoBitmap());
    }
}
