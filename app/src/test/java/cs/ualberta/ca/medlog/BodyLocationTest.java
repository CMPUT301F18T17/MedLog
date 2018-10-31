package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.Photo;

import static junit.framework.TestCase.assertEquals;

public class BodyLocationTest {

    @Test
    public void testBodyLocationPhoto(){
        Photo p = new Photo(0);
        BodyLocation bl = new BodyLocation(p, 10, 5);
        assertEquals(bl.getPhoto().getIdentifier(), p.getIdentifier());
    }

    @Test
    public void testBodyLocationX(){
        Photo p = new Photo(0);
        BodyLocation bl = new BodyLocation(p, 10, 5);
        assertEquals(bl.getX(), 10);
    }

    @Test
    public void testBodyLocationY(){
        Photo p = new Photo(0);
        BodyLocation bl = new BodyLocation(p, 10, 5);
        assertEquals(bl.getY(), 5);
    }

}
