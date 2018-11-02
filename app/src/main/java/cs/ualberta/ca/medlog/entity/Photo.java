package cs.ualberta.ca.medlog.entity;

import android.graphics.Bitmap;

public class Photo {
    private int identifier;
    private Bitmap photoBitmap;

    // Negative IDs are converted into positive IDs
    // This is a temporary fix and should be properly addressed soon
    public Photo(int id, Bitmap bitmap){
        photoBitmap = bitmap;

        if (id > 0) {
            identifier = id;
        } else {
            System.out.println("WARNING: IDs must be unique, non-negative integers");
            System.out.println("ID was converted into it's absolute value");
            identifier = Math.abs(id);
        }
    }

    public int getIdentifier(){
        return identifier;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }
}
