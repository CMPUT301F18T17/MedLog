package cs.ualberta.ca.medlog.entity;

import android.graphics.Bitmap;

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
 * @author homas Roskewich
 * @contact roskewic@ualberta.ca
 */
public class Photo implements Serializable {
    private Bitmap photoBitmap;

    public Photo(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    /**
     * <p>Get the photo as a bitmap.</p>
     * @return The bitmap of the photo.
     */
    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }
}
