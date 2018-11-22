package cs.ualberta.ca.medlog.entity;

import java.io.Serializable;

public class BodyLocation implements Serializable {
    private Photo photo;
    private int x, y;

    public BodyLocation(Photo photo, int x, int y){
        this.photo = photo;
        this.x = x;
        this.y = y;
    }


    public Photo getPhoto() {
        return photo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BodyLocation){
            BodyLocation bl = (BodyLocation) obj;
            return bl.getX() == x && bl.getY() == y && photo == bl.getPhoto();
        }
        return false;
    }
}
