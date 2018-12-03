package cs.ualberta.ca.medlog;

import cs.ualberta.ca.medlog.entity.MapLocation;

import org.junit.Test;

import static com.ibm.icu.impl.Assert.fail;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * <p>
 *     Description: <br>
 *         Test class for the MapLocation entity.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich, Tem Tamre
 * @version 1.0
 * @see MapLocation
 */
public class MapLocationTest {

    /**
     * Tests if a MapLocation's latitude is successfully returned.
     */
    @Test
    public void testGetLatitude(){
        MapLocation mapLocation = new MapLocation(15.0, -50);
        assertEquals(15.0, mapLocation.getLatitude());
    }

    /**
     * Tests if a MapLocation's longitude is successfully returned.
     */
    @Test
    public void testGetLongitude(){
        MapLocation mapLocation = new MapLocation(15.0, -50);
        assertEquals(-50.0, mapLocation.getLongitude());
    }

    /**
     * Tests if a MapLocation's latitude is successfully set and returned.
     */
    @Test
    public void testSetLatitude(){
        MapLocation mapLocation = new MapLocation(15.0, -50);
        assertEquals(15.0, mapLocation.getLatitude());

        double newLat = 20;
        mapLocation.setLatitude(newLat);
        assertEquals(newLat, mapLocation.getLatitude());
    }

    /**
     * Tests if a MapLocation's longitude is successfully set and returned.
     */
    @Test
    public void testSetLongitude(){
        MapLocation mapLocation = new MapLocation(15.0, -50);
        assertEquals(15.0, mapLocation.getLatitude());

        double newLon = 20.0;
        mapLocation.setLongitude(newLon);
        assertEquals(newLon, mapLocation.getLongitude());
    }

    /**
     * Tests if a MapLocation's latitude and longitude are limited to their numerical ranges.
     */
    @Test
    public void testIncorrectGeoLocation(){
        try {
            MapLocation mapLocation = new MapLocation(-91, 0);
            fail("Was able to create an invalid map location.");
        }catch (Exception ignored){}

        try {
            MapLocation mapLocation = new MapLocation(0, 515);
            fail("Was able to create an invalid map location.");
        }catch (Exception ignored){}

        try {
            MapLocation mapLocation = new MapLocation(-90, 180);
        }catch (Exception e){
            fail("Was able to create an invalid map location.");
        }
    }

    /**
     * Tests if one MapLocation successfully checks whether it is close to another.
     */
    @Test
    public void testIsNear(){
        MapLocation ml = new MapLocation(53.527695, -113.250598);
        MapLocation ml1 = new MapLocation(50, 100);
        MapLocation ml2 = new MapLocation(53.527205, -113.302037);
        assertFalse(ml.isNear(ml1));
        assertTrue(ml.isNear(ml2));
    }
}
