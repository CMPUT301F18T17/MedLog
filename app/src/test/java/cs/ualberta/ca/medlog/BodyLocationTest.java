/*
 * Test class for the BodyLocation entity
 *
 * Authors: Thomas Roskewich, Tem Tamre
 * Contact: roskewic@ualberta.ca, ttamre@ualberta.ca
 * Created: October 30, 2018
 */

package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.Photo;
import static junit.framework.TestCase.assertEquals;

public class BodyLocationTest {

    @Test
    public void testGetPhoto(){
        Photo photo = new Photo(null);
        BodyLocation location = new BodyLocation(photo, 10, 5);

        assertEquals(photo, location.getPhoto());
    }

    @Test
    public void testGetX(){
        Photo photo = new Photo( null);
        BodyLocation location = new BodyLocation(photo, 10, 5);

        assertEquals((int)location.getX(), 10);
    }

    @Test
    public void testGetY(){
        Photo photo = new Photo(null);
        BodyLocation location = new BodyLocation(photo, 10, 5);

        assertEquals((int)location.getY(), 5);
    }

}
