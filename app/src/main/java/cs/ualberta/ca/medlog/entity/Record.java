package cs.ualberta.ca.medlog.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Record implements Serializable {

    private String creatorUsername;
    private Date timestamp;
    private String title;
    private String comment;
    private MapLocation mapLocation;
    private BodyLocation bodyLocation;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private int id = -1;


    public Record(String creatorUsername){
        this.creatorUsername = creatorUsername;
    }


    public void setTitleComment(String title, String comment){
        this.title = title;
        this.comment = comment;
    }

    public String getUsername(){
        return creatorUsername;
    }

    public String getTitle(){
        return title;
    }

    public Boolean isValid(){
        return (title != null && comment != null) || mapLocation != null || bodyLocation != null || photos.size() > 0;
    }

    public String getComment(){
        return comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    public MapLocation getMapLocation() {
        return mapLocation;
    }

    public void setMapLocation(MapLocation mapLocation) {
        this.mapLocation = mapLocation;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }

    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}