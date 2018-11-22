package cs.ualberta.ca.medlog.entity;

import java.io.Serializable;

/**
 * <p>
 *     Description: <br>
 *         Class for holding a record body location. It contains the related body photo and the x
 *         y coordinates on the photo of the selected body location, as well as an optional label
 *         for the photograph.
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
    private String label;
    private int x, y;

    /**
     * Creates the body location with the specified body photo and x-y coordinates of the location,
     * as well as a label for the photo.
     * @param photo The body photo.
     * @param x The x coordinate of the selected location.
     * @param y The y coordinate of the selected location.
     * @param text The text for the photo label.
     */
    public BodyLocation(Photo photo, int x, int y, String text){
        this.photo = photo;
        this.x = x;
        this.y = y;
        this.label = text;
    }

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
        this.label = "";
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
     * Retrieves the label for the body location.
     * @return The label as a string.
     */
    public String getLabel() {
        return label;
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
}
