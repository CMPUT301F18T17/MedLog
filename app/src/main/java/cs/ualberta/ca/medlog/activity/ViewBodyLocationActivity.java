package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.BodyLocation;

/**
 * <p>
 *     Description: <br>
 *         The Activity for viewing a body location of a given record. It presents an image view
 *         of the related body photo with the location marked on it.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see PatientViewRecordsActivity
 */
public class ViewBodyLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_body_location);

        BodyLocation bodyLocation = (BodyLocation)getIntent().getSerializableExtra("BODY_LOCATION");

        ImageView bodyPhotoView = findViewById(R.id.activityViewBodyLocation_ImageView);
        bodyPhotoView.setImageBitmap(bodyLocation.getPhoto().getBitmap(50,50));

        ImageView marker = findViewById(R.id.activityViewBodyLocation_MarkerView);
        marker.setX(bodyPhotoView.getX() + bodyLocation.getX());
        marker.setY(bodyPhotoView.getY() + bodyLocation.getY());
    }
}
