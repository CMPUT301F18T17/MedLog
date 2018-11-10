package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient add record screen, this presents the gui for a Patient
 *         to add a new record to one of their problems. From here they can choose to proceed to
 *         add any of the four different problem fields and once they have they can save the record
 *         to the problem.
 *         The problem fields are title & comment, map location, body location and additional
 *         photos, each of which are added using fragments.
 * </p>
 * <p>
 *     Issues: <br>
 *         Transfer to a Title Comment Editor Fragment must be added.
 *         Transfer to a Body Location Selector Fragment must be added.
 *         Transfer to a Map Location Selector Fragment must be added.
 *         Transfer to a Photos Selector Fragment must be added.
 *         Must add code to add the record to the actual model.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PatientProblemViewActivity
 */
public class PatientAddRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_record);
        Button titleCommentButton = findViewById(R.id.activityPatientAddRecord_TitleCommentButton);
        Button bodyLocationButton = findViewById(R.id.activityPatientAddRecord_BodyLocationButton);
        Button mapLocationButton = findViewById(R.id.activityPatientAddRecord_MapLocationButton);
        Button photosButton = findViewById(R.id.activityPatientAddRecord_PhotosButton);
        Button saveButton = findViewById(R.id.activityPatientAddRecord_SaveButton);
        titleCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTitleCommentEditor();
            }
        });
        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyLocationSelector();
            }
        });
        mapLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocationSelector();
            }
        });
        photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotosSelector();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeRecord();
            }
        });
    }

    private void openTitleCommentEditor() {
        //TODO Add transfer to a title and comment editor fragment.
    }

    private void openBodyLocationSelector() {
        //TODO Add transfer to a body location selector fragment.
    }

    private void openMapLocationSelector() {
        //TODO Add transfer to a map location selector fragment.
    }

    private void openPhotosSelector() {
        //TODO Add transfer to a photos selector fragment.
    }

    private void completeRecord() {
        //TODO Add the completed record to the problem in the code.

        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        startActivity(intent);
    }
}
