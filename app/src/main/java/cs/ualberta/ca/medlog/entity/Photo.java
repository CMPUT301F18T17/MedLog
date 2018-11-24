package cs.ualberta.ca.medlog.entity;

import java.io.Serializable;

/**
 *
 * <h1>
 *     Photo
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to hold a representation of a photo.
 *
 * </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 */
public class Photo implements Serializable {
    private String path;
    private String id;

    public Photo(String path) {
        this.path = path;
    }

    /**
     * Get the photos path.
     * @return The photos path.
     */
    public String getPath(){
        return path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
