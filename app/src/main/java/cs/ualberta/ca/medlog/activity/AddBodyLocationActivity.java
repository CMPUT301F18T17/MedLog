package cs.ualberta.ca.medlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.Photo;

/**
 * <p>
 *     Description: <br>
 *         The Activity for adding a body location to a record. It presents an image view that
 *         presents body photos which the user can mark to set a body location. Once the user
 *         selects a location, the location is sent back to the parent activity and the
 *         AddMapLocationActivity closes.
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.4
 * @see PatientAddRecordActivity
 * @see PatientSearchActivity
 */
public class AddBodyLocationActivity extends AppCompatActivity {
    private ImageView imageView;
    private ArrayList<Photo> photos;
    private int index = 0;

    private Photo selectedPhoto;
    private float percentageX;
    private float percentageY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_body_location);

        Button previousButton = findViewById(R.id.activityAddBodyLocation_PreviousButton);
        Button nextButton = findViewById(R.id.activityAddBodyLocation_NextButton);
        Button saveButton = findViewById(R.id.activityAddBodyLocation_SaveButton);
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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBodyLocation();
            }
        });

        photos = (ArrayList<Photo>)getIntent().getSerializableExtra("BODY_PHOTOS");

        imageView = findViewById(R.id.activityAddBodyLocation_ImageView);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    percentageX = event.getX()/v.getWidth();
                    percentageY = event.getY()/v.getHeight();
                    updateLocationMarkerPlace();
                }
                return false;
            }
        });


        updateBodyPhotoDisplay();
    }

    private void displayPreviousPhoto() {
        if (index > 0) {
            index--;
            updateBodyPhotoDisplay();
        }
        else {
            Toast.makeText(this,R.string.activityAddBodyLocation_NoPrevious,Toast.LENGTH_SHORT).show();
        }
    }

    private void displayNextPhoto() {
        if (index+1 < photos.size()) {
            index++;
            updateBodyPhotoDisplay();
        }
        else {
            Toast.makeText(this,R.string.activityAddBodyLocation_NoNext,Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBodyPhotoDisplay() {
        imageView.setImageBitmap(photos.get(index).getBitmap());
        TextView labelView = findViewById(R.id.activityAddBodyLocation_BodyPhotoLabelView);
        labelView.setText(photos.get(index).getLabel());
        selectedPhoto = null;
        ImageView markerView = findViewById(R.id.activityAddBodyLocation_MarkerView);
        markerView.setVisibility(View.INVISIBLE);

    }

    private void updateLocationMarkerPlace() {
        selectedPhoto = photos.get(index);
        ImageView markerView = findViewById(R.id.activityAddBodyLocation_MarkerView);
        markerView.setVisibility(View.VISIBLE);
        Toast.makeText(this,imageView.getWidth() + " " + imageView.getHeight(),Toast.LENGTH_SHORT).show();
        markerView.setX((imageView.getWidth()*percentageX) - markerView.getWidth()/4);
        markerView.setY((imageView.getHeight()*percentageY) - markerView.getHeight()/4);
    }

    private void saveBodyLocation() {
        if (selectedPhoto == null) {
            Toast.makeText(this,R.string.activityAddBodyLocation_NoLocation,Toast.LENGTH_SHORT).show();
            return;
        }

        BodyLocation location = new BodyLocation(selectedPhoto,percentageX,percentageY);

        Intent intent = new Intent();
        intent.putExtra("BODY_LOCATION",location);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
