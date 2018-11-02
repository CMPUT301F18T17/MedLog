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
    private ArrayList<Photo> photos;


    public Record(String creatorUsername){
        this.creatorUsername = creatorUsername;
    }


    void setTitleComment(String title, String comment){
        this.title = title;
        this.comment = comment;
    }

    String getTitle(){
        return title;
    }

    String getComment(){
        return comment;
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
}
