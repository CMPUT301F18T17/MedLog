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
 * @author Thomas Roskewich, Tem Tamre
 * @version 1.0
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

        assertEquals((int)location.getX(), 0.1f);
    }

    /**
     * Tests if a bodyLocations y percentage on the photo is successfully returned.
     */
    @Test
    public void testGetY(){
        Photo photo = new Photo(null);
        BodyLocation location = new BodyLocation(photo, 10, 5);

        assertEquals((int)location.getY(), 5);
    }

}
