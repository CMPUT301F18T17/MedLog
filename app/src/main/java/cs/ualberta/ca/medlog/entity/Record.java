/**
 *
 * <h1>
 *     Record
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         This class represents the multiple record types which users can save.
 *
 * </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 * @see cs.ualberta.ca.medlog.entity.Problem
 */

package cs.ualberta.ca.medlog.entity;

import java.util.ArrayList;
import java.util.Date;

public class Record {

    private String creatorUsername;
    private Date timestamp;
    private String title;
    private String comment;
    private MapLocation mapLocation;
    private BodyLocation bodyLocation;
    private ArrayList<Photo> photos = new ArrayList<>();
    private int id = -1;


    public Record(String creatorUsername){
        this.creatorUsername = creatorUsername;
        this.timestamp = new Date();
    }


    /**
     * <p>Set the records title and comment.</p>
     * @param title The title to be set.
     * @param comment The comment to be set.
     */
    public void setTitleComment(String title, String comment){
        this.title = title;
        this.comment = comment;
    }

    /**
     * <p>Get the record creators username.</p>
     * @return The username of the creator.
     */
    public String getUsername(){
        return creatorUsername;
    }

    /**
     * <p>Get the title of the record. </p>
     * @return The title.
     */
    public String getTitle(){
        return title;
    }

    /**
     * <p>Check if the record is a valid record, i.e. title comment maplocation body location or a photo is added.</p>
     * @return A boolean if its valid.
     */
    public Boolean isValid(){
        return (title != null && comment != null) || mapLocation != null || bodyLocation != null || photos.size() > 0;
    }

    /**
     * <p>Get the comment of the record.</p>
     * @return The comment.
     */
    public String getComment(){
        return comment;
    }

    /**
     * <p>Get the timestamp.</p>
     * @return The date timestamp.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * <p>Set the timestamp.</p>
     * @param date The new date to set.
     */
    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    /**
     * <p>Get the map location</p>
     * @return The map location.
     */
    public MapLocation getMapLocation() {
        return mapLocation;
    }

    /**
     * <p>Set the map location.</p>
     * @param mapLocation The new map location.
     */
    public void setMapLocation(MapLocation mapLocation) {
        this.mapLocation = mapLocation;
    }

    /**
     * <p>Get the body location.</p>
     * @return The body location.
     */
    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    /**
     * <p>Set the body location.</p>
     * @param bodyLocation The new body location/
     */
    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /**
     * <p>Set the photos for a record.</p>
     * @param newPhotos The new photos.
     */
    public void setPhotos(ArrayList<Photo> newPhotos){ photos = newPhotos; }

    /**
     * <p>Gets the photos in the record.</p>
     * @return An array of photos.
     */
    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    /**
     * <p>Get the record id.</p>
     * @return The record id.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>Set the record id.</p>
     * @param id the new record id.
     */
    public void setId(int id) {
        this.id = id;
    }
}