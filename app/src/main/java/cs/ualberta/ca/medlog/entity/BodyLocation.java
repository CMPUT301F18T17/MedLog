package cs.ualberta.ca.medlog.entity;

public class BodyLocation {
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
}
