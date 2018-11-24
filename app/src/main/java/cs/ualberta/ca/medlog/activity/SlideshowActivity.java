package cs.ualberta.ca.medlog.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Record;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a photo slideshow screen, this presents the gui for a user to view a
 *         group of photos in a slideshow format.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see PatientProblemViewActivity
 * @see ProviderProblemViewActivity
 * @see PatientRecordViewActivity
 * @see ProviderRecordViewActivity
 */
public class SlideshowActivity extends AppCompatActivity {
    private ImageView imageView;
    private ArrayList<Bitmap> photoBitmaps;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        Button previousButton = findViewById(R.id.activitySlideshow_PreviousButton);
        Button nextButton = findViewById(R.id.activitySlideshow_NextButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPreviousPhoto();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextPhoto();
            }
        });

        retrievePhotoBitmaps((ArrayList<Photo>)getIntent().getSerializableExtra("PHOTOS"));

        imageView = findViewById(R.id.activitySlideshow_ImageView);
        imageView.setImageBitmap(photoBitmaps.get(index));
    }

    private void retrievePhotoBitmaps(ArrayList<Photo> photos) {
        photoBitmaps = new ArrayList<>();
        for(Photo photo: photos) {
            photoBitmaps.add(photo.getBitmap(50,50));
        }
    }

    private void displayPreviousPhoto() {
        if (index > 0) {
            imageView.setImageBitmap(photoBitmaps.get(--index));
        }
        else {
            Toast.makeText(this,"No previous photo",Toast.LENGTH_SHORT).show();
        }
    }

    private void displayNextPhoto() {
        if (index+1 < photoBitmaps.size()) {
            imageView.setImageBitmap(photoBitmaps.get(++index));
        }
        else {
            Toast.makeText(this,"No next photo",Toast.LENGTH_SHORT).show();
        }
    }
}
