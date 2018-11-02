package cs.ualberta.ca.medlog;

import org.junit.Test;

import cs.ualberta.ca.medlog.entity.Photo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PhotoTest {

    @Test
    public void testPhoto(){
        Photo p = new Photo(34, null);
        assertEquals(p.getIdentifier(), 34);
    }

    @Test
    public void testNegId(){
        Photo p;
        try {
            p = new Photo(-1, null);
        }catch(RuntimeException e){
            p = null;
        }
        assertNull(p);
    }
}
