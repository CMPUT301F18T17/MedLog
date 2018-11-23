package cs.ualberta.ca.medlog.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

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
public class Photo implements Parcelable {
    private Bitmap photoBitmap;

    public Photo(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    private Photo(Parcel in) {
        photoBitmap = in.readParcelable(ClassLoader.getSystemClassLoader());
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(photoBitmap,0);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    /**
     * <p>Get the photo as a bitmap.</p>
     * @return The bitmap of the photo.
     */
    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }


}
