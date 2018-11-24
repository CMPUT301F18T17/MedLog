package cs.ualberta.ca.medlog.entity;

import java.io.Serializable;

/**
 * <p>
 *     Description: <br>
 *         Class for holding a record body location. It contains the related body photo and the x
 *         y coordinates on the photo of the selected body location.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.1
 * @see Photo
 */
public class BodyLocation implements Serializable {
    private Photo photo;
    private int x, y;

    private static final double SEARCHDISTBODY = 25;

    /**
     * Creates the body location with the specified body photo and x-y coordinates of the location.
     * @param photo The body photo.
     * @param x The x coordinate of the selected location.
     * @param y The y coordinate of the selected location.
     */
    public BodyLocation(Photo photo, int x, int y){
        this.photo = photo;
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the body photo related to the given body location.
     * @return The body photo.
     */
    public Photo getPhoto() {
        return photo;
    }

    /**
     * Retrieves the x coordinate of the body location.
     * @return The x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the y coordinate of the body location.
     * @return The y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if two body locations are equal.
     * @param obj The other body location object
     * @return True if they are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BodyLocation){
            BodyLocation bl = (BodyLocation) obj;
            return bl.getX() == x && bl.getY() == y && photo == bl.getPhoto();
        }
        return false;
    }

    /**
     * Returns if a body location is near to another location.
     * @param bl The body location to compare against.
     * @return If its less than or equal to the max dist.
     */
    public boolean isNear(BodyLocation bl){
        double dist = Math.sqrt(Math.pow(x - bl.x, 2) + Math.pow(y - bl.y, 2));
        return dist <= SEARCHDISTBODY;
    }
}
