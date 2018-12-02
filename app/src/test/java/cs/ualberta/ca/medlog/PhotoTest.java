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
    public void testCreatePhoto() {
        Photo p = new Photo("/d/test.png");
        assertNotNull(p);
    }

    @Test
    public void testGetPath() {
        Photo p = new Photo("/d/test.png");
        assertEquals(p.getPath(),"/d/test.png");
    }

    @Test
    public void testId() {
        Photo p = new Photo("/d/test.png");
        assertNull(p.getId());
        String id = "Test ID";
        p.setId(id);
        assertEquals(p.getId(),"Test ID");
    }

    @Test
    public void testGetPhotoBitmap() {
        Photo p = new Photo("/d/test.png");
        assertNotNull(p.getBitmap());
    }

    @Test
    public void testLabel() {
        Photo p = new Photo("/d/test.png");
        assertEquals(p.getLabel(), "");
        String newLabel = "Test Label";
        p.setLabel(newLabel);
        assertEquals(p.getLabel(),"Test Label");
    }
}
