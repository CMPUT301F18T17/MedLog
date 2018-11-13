package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Record;

/**
 * <p>
 *     Description: <br>
 *         The patient record viewing activity screen for the Application, this presents the gui
 *         for the patient to view one of their record's data of the title of the problem that owns
 *         it, the timestamp of its creation and the user that created it.
 *         Additional information is present in the fragments/activities the patient can proceed to
 *         where they can view the record title & comment, body location, map location or any
 *         attached photos in a slideshow.
 * </p>
 * <p>
 *     Issues: <br>
 *         A call to the system to get the currently logged in patient's problems and records must be added.
 *         Updating the record data display fields to match the given record on open must be added.
 *         Transfer to a title & comment fragment must be added
 *         Transfer to a body location fragment must be added
 *         Transfer to a map location fragment must be added
 *
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.2
 * @see PatientViewRecordsActivity
 * @see PatientSearchActivity
 * @see SlideshowActivity
 */
public class PatientRecordViewActivity extends AppCompatActivity {
    private Record record;
    int problemIndex;
    int recordIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);

        Intent intent = getIntent();
        problemIndex = intent.getIntExtra("problemIndex",0);
        recordIndex = intent.getIntExtra("recordIndex",0);
        //TODO Call to the system to retrieve the record found by the given indexes
        //TODO Setting the record object to the returned problem's record

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
                openPhotoSlideshow();
            }
        });

        //TODO Read data from the given problem to display in the related fields.
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

    private void openPhotoSlideshow() {
        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("problemIndex",problemIndex);
        intent.putExtra("recordIndex",recordIndex);
        startActivity(intent);
    }
}
