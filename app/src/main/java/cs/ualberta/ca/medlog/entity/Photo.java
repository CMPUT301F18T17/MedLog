package cs.ualberta.ca.medlog.entity;

import android.graphics.Bitmap;

public class Photo {
    private int identifier;
    private Bitmap photoBitmap;

    public Photo(int id, Bitmap photoBitmap){
        identifier = id;
        this.photoBitmap = photoBitmap;
    }

    public int getIdentifier(){
        return identifier;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }
}
