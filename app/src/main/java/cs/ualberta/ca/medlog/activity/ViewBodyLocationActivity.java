package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView imageView;
    private BodyLocation bodyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_body_location);

        bodyLocation = (BodyLocation)getIntent().getSerializableExtra("BODY_LOCATION");

        imageView = findViewById(R.id.activityViewBodyLocation_ImageView);
        imageView.setImageBitmap(bodyLocation.getPhoto().getBitmap());

        TextView labelView = findViewById(R.id.activityViewBodyLocation_BodyPhotoLabelView);
        labelView.setText(bodyLocation.getPhoto().getLabel());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            ImageView markerView = findViewById(R.id.activityViewBodyLocation_MarkerView);
            markerView.setX((imageView.getWidth()*bodyLocation.getX()) - markerView.getWidth()/4);
            markerView.setY((imageView.getHeight()*bodyLocation.getY()) - markerView.getHeight()/4);
        }
    }
}
