package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.MapLocation;

import static junit.framework.TestCase.assertEquals;

public class MapLocationTest {

    @Test
    public void testMapLocation(){
        MapLocation ml = new MapLocation(15, 516);
        assertEquals(ml.getX(),15);
        assertEquals(ml.getY(), 516);
    }
}
