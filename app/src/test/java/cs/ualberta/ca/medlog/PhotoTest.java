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
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class PhotoTest {

    @Test
    public void testGetPhotoBitmap() {
        Photo p = new Photo("/d/test.png");
        assertNotNull(p.getBitmap());
    }
}
