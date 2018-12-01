package cs.ualberta.ca.medlog.mock;

import android.graphics.Bitmap;

import cs.ualberta.ca.medlog.entity.Photo;

/**
 * <p>
 *     Description: <br>
 *         This class is a mock representation of the photo class. This can be used in testing.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.0
 * @see Photo
 */
public class MockPhoto extends Photo{

    private int width;
    private int height;

    public MockPhoto(int width, int height) {
        super("NULL");
        this.width = width;
        this.height = height;
    }

    /**
     * Retrieves a bitmap of the given photo. First checks if one is already cached, if not loads
     * from memory again.
     * @return The bitmap.
     */
    @Override
    public Bitmap getBitmap() {
        if (bitmap == null || bitmap.isRecycled()) {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            bitmap = Bitmap.createBitmap(width, height, config);
        }

        return bitmap;
    }
}
