package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Record;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider patient record view screen, this presents the gui for
 *         the Provider to view one of their patient's records data in terms of the problem that
 *         owns it, the timestamp of its creation and the user that created it.
 *         Further ability is present to open screens/fragments to view the four fields a problem can
 *         contain of a title & comment, a body location, a map location and an attached photo
 *         slideshow.
 *         Additionally if the creator is a Patient the username can be clicked to travel to the
 *         patient profile.
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
 * @see ProviderPatientViewRecordsActivity
 * @see ProviderSearchActivity
 * @see ProviderPatientProfileActivity
 * @see SlideshowActivity
 */
public class ProviderRecordViewActivity extends AppCompatActivity {
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_record_view);

        Intent intent = getIntent();
        String problemTitle = intent.getStringExtra("PROBLEM_TITLE");
        record = intent.getSerializableExtra("RECORD");

        Button titleCommentButton = findViewById(R.id.activityProviderRecordView_TitleCommentButton);
        Button bodyLocationButton = findViewById(R.id.activityProviderRecordView_BodyLocationButton);
        Button mapLocationButton = findViewById(R.id.activityProviderRecordView_MapLocationButton);
        Button slideshowButton = findViewById(R.id.activityProviderRecordView_SlideshowButton);
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

        TextView creatorView = findViewById(R.id.activityProviderRecordView_CreatorView);
        creatorView.setText(record.getUsername());
        creatorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientProfile();
            }
        });

        TextView problemTitleView = findViewById(R.id.activityProviderRecordView_ProblemTitleView);
        problemTitleView.setText(problemTitle);
        TextView timestampView = findViewById(R.id.activityProviderRecordView_TimestampView);
        timestampView.setText(record.getTimestamp().toString());
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
        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("PHOTOS",record.getPhotos());
        startActivity(intent);
    }

    private void openPatientProfile() {
        Database db = new Database(this);
        Patient toOpen;
        try {
            toOpen = db.loadPatient(record.getUsername());
        } catch(UserNotFoundException e) {
            Toast.makeText(this,"Patient doesn't exist", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);
        intent.putExtra("PATIENT", toOpen);
        startActivity(intent);
    }
}
