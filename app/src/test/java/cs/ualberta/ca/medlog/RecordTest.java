package cs.ualberta.ca.medlog;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Photo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 *     Description: <br>
 *         Test class for the Record entity.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Calvin Chomyc, Thomas Roskewich, Kyle Androschuk
 * @version 1.0
 * @see Record
 */
public class RecordTest {

    /**
     * Tests if a Record is successfully created.
     */
    @Test
    public void testRecordCreation(){
        String username = "John Doe";
        Record testRecord = new Record(username);
        assertNotNull(testRecord);
    }

    /**
     * Tests if a Record's id is successfully set and returned.
     */
    @Test
    public void testId(){
        Record testRecord = new Record("John Doe");

        assertEquals(-1, testRecord.getId());
        testRecord.setId(5);
        assertEquals(5, testRecord.getId());
    }

    /**
     * Tests if a Record's title and comment are successfully set and returned.
     */
    @Test
    public void testTitleComment(){
        Record testRecord = new Record("John Does");
        String title = "Title for a Record";
        String comment = "This is a record comment.";
        testRecord.setTitleComment(title, comment);
        assertEquals(testRecord.getTitle(), title);
        assertEquals(testRecord.getComment(), comment);
    }

    /**
     * Tests if a Record's timestamp is successfully set and returned.
     */
    @Test
    public void testTimestamp(){
        Record testRecord = new Record("John Doe");

        Date timestamp = new Date();
        assertNotNull(testRecord.getTimestamp());
        testRecord.setTimestamp(timestamp);
        assertEquals(testRecord.getTimestamp(), timestamp);
    }

    /**
     * Tests if a Record's BodyLocation is successfully set and returned.
     */
    @Test
    public void testBodyLocation(){
        Record testRecord = new Record("John Doe");
        Photo photo = new Photo(null);

        BodyLocation bodyLocation = new BodyLocation(photo, 10, 20);
        assertNull(testRecord.getBodyLocation());
        testRecord.setBodyLocation(bodyLocation);
        assertEquals(testRecord.getBodyLocation(), bodyLocation);
    }

    /**
     * Tests if a Record's MapLocation is successfully set and returned.
     */
    @Test
    public void testMapLocation(){
        Record testRecord = new Record("John Doe");

        MapLocation mapLocation = new MapLocation(10, 20);
        assertNull(testRecord.getMapLocation());
        testRecord.setMapLocation(mapLocation);
        assertEquals(testRecord.getMapLocation(), mapLocation);
    }

    /**
     * Tests if Photos are successfully added to and returned from a Record.
     */
    @Test
    public void testPhotos() {
        Record testRecord = new Record("John Doe");

        ArrayList<Photo> photoList = new ArrayList<Photo>(5);
        assertTrue(testRecord.getPhotos().isEmpty());
        ArrayList<Photo> photos = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo(null);
            photos.add(i,photo);
            testRecord.setPhotos(photos);
            photoList.add(i,photo);

            assertEquals(testRecord.getPhotos(), photoList);
            assertEquals(i + 1, testRecord.getPhotos().size());
        }
    }

    /**
     * Tests if a Record successfully determines whether it contains data.
     */
    @Test
    public void testIsValid() {
        Record testRecord = new Record("John Doe");
        assertFalse(testRecord.isValid());

        String title = "Title for a Record";
        String comment = "This is a record comment.";
        testRecord.setTitleComment(title, comment);
        assertTrue(testRecord.isValid());
        testRecord.setTitleComment(null, null);
        assertFalse(testRecord.isValid());

        BodyLocation bodyLocation = new BodyLocation(null, 10, 20);
        testRecord.setBodyLocation(bodyLocation);
        assertTrue(testRecord.isValid());
        testRecord.setBodyLocation(null);
        assertFalse(testRecord.isValid());

        MapLocation mapLocation = new MapLocation(10, 20);
        testRecord.setMapLocation(mapLocation);
        assertTrue(testRecord.isValid());
        testRecord.setMapLocation(null);
        assertFalse(testRecord.isValid());

        ArrayList<Photo> photoList = new ArrayList<Photo>();
        Photo photo = new Photo(null);
        photoList.add(photo);
        testRecord.setPhotos(photoList);
        assertTrue(testRecord.isValid());
        testRecord.setPhotos(new ArrayList<Photo>());
        assertFalse(testRecord.isValid());
    }
}