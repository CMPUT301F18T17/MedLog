/*
 * Test class for the Photo entity
 *
 * Authors: Thomas Roskewich, Tem Tamre
 * Contact: roskewic@ualberta.ca, ttamre@ualberta.ca
 * Created: October 30, 2018
 */

package cs.ualberta.ca.medlog;

import org.junit.Test;
import cs.ualberta.ca.medlog.entity.Photo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PhotoTest {

    @Test
    public void testPhoto(){
        // Positive ID (works as expected)
        Photo p1 = new Photo(1000, null);
        assertEquals(p1.getIdentifier(), 1000);

        // Negative ID (Uses the absolute value of that ID)
        Photo p2 = new Photo(-1000, null);
        assertEquals(p2.getIdentifier(), 1000);
    }

    @Test
    public void testGetPhotoBitmap() {
        Photo p = new Photo(1000, null);
        assertNull(p.getPhotoBitmap());
    }
}
