package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient record view screen, this presents the gui for the Patient
 *         to view one of their records data in terms of the problem that owns it, the timestamp
 *         of its creation and the user that created it.
 *         Further ability is present to open fragments to view the four fields a problem can
 *         contain of a title & comment, a body location, a map location and an attached
 *         slideshow.
 * </p>
 * <p>
 *     Issues: <br>
 *         Actual code to read a record and present it must be added.
 *         Transfer to a title & comment fragment must be added
 *         Transfer to a body location fragment must be added
 *         Transfer to a map location fragment must be added
 *         Transfer to a photos slideshow fragment must be added
 *
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PatientViewRecordsActivity
 * @see PatientSearchActivity
 */
public class PatientRecordViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);
        Button titleCommentButton = findViewById(R.id.activityPatientRecordView_TitleCommentButton);
        Button bodyLocationButton = findViewById(R.id.activityPatientRecordView_BodyLocationButton);
        Button mapLocationButton = findViewById(R.id.activityPatientRecordView_MapLocationButton);
        Button slideshowButton = findViewById(R.id.activityPatientRecordView_SlideshowButton);
        titleCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTitleCommentFragment();
            }
        });
        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyLocationFragment();
            }
        });
        mapLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocationFragment();
            }
        });
        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSlideshowFragment();
            }
        });

        //TODO Add code to receive a provided record object and set the related fields to its data.
    }

    private void openTitleCommentFragment() {
        //TODO Add transfer to title & comment fragment
    }

    private void openBodyLocationFragment() {
        //TODO Add transfer to body location fragment
    }

    private void openMapLocationFragment() {
        //TODO Add transfer to map location fragment
    }

    private void openSlideshowFragment() {
        //TODO Add transfer to slideshow fragment
    }
}
