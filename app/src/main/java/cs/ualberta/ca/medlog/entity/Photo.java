package cs.ualberta.ca.medlog.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;


/**
 * <p>
 *     Description: <br>
 *         The purpose of this class is to hold a representation of a photo in terms of its path in
 *         the files and potentially a bitmap that can be grabbed again. As well as an optional
 *         label for the photograph if it is a body photo.

 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.0
 */
public class Photo implements Serializable {
    private String path;
    private String id;
    private Bitmap bitmap;
    private String label;

    /**
     * Creates the photo object with a path to its file in storage.
     * @param path The path to its file in storage.
     */
    public Photo(String path) {
        this.path = path;
        this.label = "";
    }

    /**
     * Retrieves the label for the photo.
     * @return The label as a string.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label for the photo.
     * @param newLabel The new label.
     */
    public void setLabel(String newLabel) {
        this.label = newLabel;
    }

    /**
     * Get the photos path.
     * @return The photos path.
     */
    public String getPath(){
        return path;
    }

    /**
     * Retrieves the unique id for the photo.
     * @return The id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique id for the photo.
     * @param id The id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves a bitmap of the given photo. First checks if one is alread cached, if not loads
     * from memory again.
     * @return The bitmap.
     */
    public Bitmap getBitmap() {
        if (bitmap == null || bitmap.isRecycled()) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bmOptions);
            bmOptions.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, bmOptions);
        }

        return bitmap;
    }


}
