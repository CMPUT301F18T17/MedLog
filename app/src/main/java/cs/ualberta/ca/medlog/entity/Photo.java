package cs.ualberta.ca.medlog.entity;

        import android.graphics.Bitmap;

public class Photo {
    private int identifier;
    private Bitmap photoBitmap;

    public Photo(int id, Bitmap photoBitmap) {
        identifier = id;
        this.photoBitmap = photoBitmap;
        if (identifier < 0) { // If identifier is negative
            throw new RuntimeException();
        }
    }

    public int getIdentifier(){
        return identifier;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }
}
