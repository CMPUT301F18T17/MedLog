package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a Care Provider's patient profile viewing screen, this presents the
 *         gui for a Care Provider to view their patients contact info and to open a screen to
 *         view a list of their problems.
 *         Additionally there is a button to open a fragment showing a map of all the patients
 *         map location included records.
 * </p>
 * <p>
 *     Issues: <br>
 *         Transfer to a Map of all Records view must be added.
 *         Actual code to read a users details must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.2
 * @see ProviderViewPatientsActivity
 * @see ProviderAddPatientActivity
 * @see ProviderPatientViewProblemsActivity
 */
public class ProviderPatientProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_patient_profile);
        Button reccordsMapButton = findViewById(R.id.activityProviderPatientProfile_RecordsMapButton);
        Button viewRecordsButton = findViewById(R.id.activityProviderPatientProfile_ViewRecordsButton);
        reccordsMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordsMap();
            }
        });
        viewRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientProblemsList();
            }
        });

        //TODO Add code to receive a provided patient object and set the related fields to its data.
    }

    private void openRecordsMap() {
        //TODO Add code to open a records map fragment.
    }

    private void openPatientProblemsList() {
        Intent intent = new Intent(this, ProviderPatientViewProblemsActivity.class);
        startActivity(intent);
    }
}
