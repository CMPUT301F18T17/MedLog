/*
 * Test class for the MapLocation entity
 *
 * Authors: Thomas Roskewich, Tem Tamre
 * Contact: roskewic@ualberta.ca, ttamre@ualberta.ca
 * Created: October 30, 2018
 */

package cs.ualberta.ca.medlog;

import cs.ualberta.ca.medlog.entity.MapLocation;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class MapLocationTest {

    @Test
    public void testGetLatitude(){
        MapLocation mapLocation = new MapLocation(15.0, 516.0);
        assertEquals(15.0, mapLocation.getLatitude());
    }

    @Test
    public void testGetLongitude(){
        MapLocation mapLocation = new MapLocation(15.0, 516.0);
        assertEquals(516.0, mapLocation.getLongitude());
    }

    @Test
    public void testSetLatitude(){
        MapLocation mapLocation = new MapLocation(15.0, 516.0);
        assertEquals(15.0, mapLocation.getLatitude());

        double newLat = 20;
        mapLocation.setLatutude(newLat);
        assertEquals(newLat, mapLocation.getLatitude());
    }

    @Test
    public void testSetLongitude(){
        MapLocation mapLocation = new MapLocation(15.0, 516.0);
        assertEquals(15.0, mapLocation.getLatitude());

        double newLon = 20.0;
        mapLocation.setLongitude(newLon);
        assertEquals(newLon, mapLocation.getLongitude());
    }
}
