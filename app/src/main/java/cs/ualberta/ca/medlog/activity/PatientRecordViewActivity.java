package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
 *         Transfer to a title & comment fragment must be added
 *         Transfer to a body location fragment must be added
 *         Transfer to a map location fragment must be added
 *
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.5
 * @see PatientViewRecordsActivity
 * @see PatientSearchActivity
 * @see SlideshowActivity
 */
public class PatientRecordViewActivity extends AppCompatActivity {
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_view);

        Intent intent = getIntent();
        String problemTitle = intent.getStringExtra("PROBLEM_TITLE");
        record = (Record) intent.getSerializableExtra("RECORD");

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
                openMapLocationActivity();
            }
        });
        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoSlideshow();
            }
        });

        TextView problemTitleView = findViewById(R.id.activityPatientRecordView_ProblemTitleView);
        problemTitleView.setText(problemTitle);
        TextView creatorView = findViewById(R.id.activityPatientRecordView_CreatorView);
        creatorView.setText(record.getUsername());
        TextView timestampView = findViewById(R.id.activityPatientRecordView_TimestampView);
        timestampView.setText(record.getTimestamp().toString());
    }

    private void openTitleCommentFragment() {
        //TODO Add transfer to title & comment fragment
    }

    private void openBodyLocationFragment() {
        //TODO Add transfer to body location fragment
    }

    private void openMapLocationActivity() {
        Intent intent = new Intent(this, ViewMapLocationActivity.class);
        intent.putExtra("record",record);
        startActivity(intent);
    }

    private void openPhotoSlideshow() {
        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("PHOTOS",record.getPhotos());
        startActivity(intent);
    }
}
