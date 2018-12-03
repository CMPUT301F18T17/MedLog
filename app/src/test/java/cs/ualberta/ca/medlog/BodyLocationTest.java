package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.Photo;
import static junit.framework.TestCase.assertEquals;

/**
 * <p>
 *     Description: <br>
 *         Test class for the BodyLocation entity.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich, Tem Tamre, Calvin Chomyc
 * @version 1.1
 */
public class BodyLocationTest {

    /**
     * Tests if a BodyLocations photo is successfully returned.
     */
    @Test
    public void testGetPhoto(){
        Photo photo = new Photo(null);
        BodyLocation location = new BodyLocation(photo, 10, 5);

        assertEquals(photo, location.getPhoto());
    }

    /**
     * Tests if a bodyLocations x percentage on the photo is successfully returned.
     */
    @Test
    public void testGetX(){
        Photo photo = new Photo( null);
        BodyLocation location = new BodyLocation(photo, 0.1f, 0.5f);

        assertEquals(location.getX(), 0.1f);
    }

    /**
     * Tests if a bodyLocations y percentage on the photo is successfully returned.
     */
    @Test
    public void testGetY(){
        Photo photo = new Photo(null);
        BodyLocation location = new BodyLocation(photo, 10, 5.8f);

        assertEquals(location.getY(), 5.8f);
    }

    /**
     * Tests if a bodyLocations y percentage on the photo is successfully returned.
     */
    @Test
    public void testEquals(){
        Photo photo = new Photo(null);
        BodyLocation location1 = new BodyLocation(photo, 10, 0.8f);
        BodyLocation location2 = new BodyLocation(photo, 10, 0.8f);

        assertEquals(location1, location2);
    }
}
