package cs.ualberta.ca.medlog.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 *     Description: <br>
 *         This class represents a problem's record in the application, records possess a id,
 *         creator username and timestamp. Furthermore they can possess titles, comments map
 *         locations, body locations and photos. There are also getters and setters for each of
 *         these components.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich, Calvin Chomyc, Tem Tamre
 * @version 1.2
 * @see MapLocation
 * @see BodyLocation
 * @see Photo
 */
public class Record {
    private int id = -1;
    private String creatorUsername;
    private Date timestamp;

    private String title;
    private String comment;
    private MapLocation mapLocation;
    private BodyLocation bodyLocation;
    private ArrayList<Photo> photos = new ArrayList<>();

    /**
     * Constructs a new Record with the given creator username. Sets the timestamp to the current
     * date.
     * @param creatorUsername The username.
     */
    public Record(String creatorUsername){
        this.creatorUsername = creatorUsername;
        this.timestamp = new Date();
    }

    /**
     * Retrieves the record id.
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the record's id to the provided number.
     * @param id The new record id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the record creator's username.
     * @return The username.
     */
    public String getUsername(){
        return creatorUsername;
    }

    /**
     * Retrieves the timestamp for the record.
     * @return The timestamp as a Date.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the record's timestamp to the provided Date.
     * @param date The new timestamp.
     */
    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    /**
     * Retrieves the title for the record.
     * @return The title.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Retrieves the comment for the record.
     * @return The comment.
     */
    public String getComment(){
        return comment;
    }

    /**
     * Sets the record's title and comment to the provided values.
     * @param title The new title.
     * @param comment The new comment.
     */
    public void setTitleComment(String title, String comment){
        this.title = title;
        this.comment = comment;
    }

    /**
     * Retrieves the record map location.
     * @return The map location.
     */
    public MapLocation getMapLocation() {
        return mapLocation;
    }

    /**
     * Sets the record's map location to the provided one.
     * @param mapLocation The new map location.
     */
    public void setMapLocation(MapLocation mapLocation) {
        this.mapLocation = mapLocation;
    }

    /**
     * Retrieves the record body location.
     * @return The body location.
     */
    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    /**
     * Sets the record's body location to the provided one.
     * @param bodyLocation The new body location.
     */
    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /**
     * Retrieves the record's photos.
     * @return ArrayList of the record's photos.
     */
    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    /**
     * Sets the record's photos to the provided ones.
     * @param newPhotos ArrayList of new record photos.
     */
    public void setPhotos(ArrayList<Photo> newPhotos){ photos = newPhotos; }

    /**
     * Checks if the record is valid, such that one of title&comment, map location, body location
     * or photos are non-null.
     * @return Boolean of if the record is valid.
     */
    public Boolean isValid(){
        return (title != null && comment != null) || mapLocation != null || bodyLocation != null || photos.size() > 0;
    }
}