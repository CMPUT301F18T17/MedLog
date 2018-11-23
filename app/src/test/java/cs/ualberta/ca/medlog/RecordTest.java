package cs.ualberta.ca.medlog;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Photo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecordTest {

    @Test
    public void testRecordCreation(){
        String username = "John Doe";
        Record testRecord = new Record(username);
    }

    @Test
    public void testTitleComment(){
        Record testRecord = new Record("John Does");
        String title = "Title for a Record";
        String comment = "This is a record comment.";
        testRecord.setTitleComment(title, comment);
        assertEquals(testRecord.getTitle(), title);
        assertEquals(testRecord.getComment(), comment);
    }

    @Test
    public void testTimestamp(){
        Record testRecord = new Record("John Doe");

        Date timestamp = new Date();
        assertNotNull(testRecord.getTimestamp());
        testRecord.setTimestamp(timestamp);
        assertEquals(testRecord.getTimestamp(), timestamp);
    }

    @Test
    public void testBodyLocation(){
        Record testRecord = new Record("John Doe");
        Photo photo = new Photo(null);

        BodyLocation bodyLocation = new BodyLocation(photo, 10, 20);
        assertNull(testRecord.getBodyLocation());
        testRecord.setBodyLocation(bodyLocation);
        assertEquals(testRecord.getBodyLocation(), bodyLocation);
    }

    @Test
    public void testMapLocation(){
        Record testRecord = new Record("John Doe");

        MapLocation mapLocation = new MapLocation(10, 20);
        assertNull(testRecord.getMapLocation());
        testRecord.setMapLocation(mapLocation);
        assertEquals(testRecord.getMapLocation(), mapLocation);
    }

    @Test
    public void testPhotos() {
        Record testRecord = new Record("John Doe");

        ArrayList<Photo> photoList = new ArrayList<Photo>();
        assertTrue(testRecord.getPhotos().isEmpty());

        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo(null);
            testRecord.addPhoto(photo);
            photoList.add(photo);

            assertEquals(testRecord.getPhotos(), photoList);
            assertEquals(i + 1, testRecord.getPhotos().size());
        }
    }
}