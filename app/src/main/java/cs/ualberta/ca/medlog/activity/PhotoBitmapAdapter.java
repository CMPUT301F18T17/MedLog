package cs.ualberta.ca.medlog.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         Custom Array Adapter for presenting one with Image Views based on provided Bitmaps.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 */
public class PhotoBitmapAdapter extends ArrayAdapter<Bitmap> {
    public PhotoBitmapAdapter(Context context, ArrayList<Bitmap> photoBitmapList) {
        super(context,0,photoBitmapList);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_item,parent,false);
        }

        ImageView photoView = convertView.findViewById(R.id.viewPhoto_PhotoView);
        photoView.setImageBitmap(getItem(position));

        return convertView;
    }
}
