package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

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
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see ProviderViewPatientsActivity
 * @see ProviderAddPatientActivity
 * @see ProviderPatientViewProblemsActivity
 * @see ViewMapLocationActivity
 */
public class ProviderPatientProfileActivity extends AppCompatActivity {
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_patient_profile);

        patient = AppStatus.getInstance().getViewedPatient();

        Button recordsMapButton = findViewById(R.id.activityProviderPatientProfile_RecordsMapButton);
        Button viewRecordsButton = findViewById(R.id.activityProviderPatientProfile_ViewRecordsButton);
        recordsMapButton.setOnClickListener(new View.OnClickListener() {
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

        TextView usernameView = findViewById(R.id.activityProviderPatientProfile_UsernameView);
        usernameView.setText(patient.getUsername());
        TextView emailView = findViewById(R.id.activityProviderPatientProfile_EmailView);
        emailView.setText(patient.getContactInfo().getEmail());
        TextView phoneNumberView = findViewById(R.id.activityProviderPatientProfile_PhoneNumberView);
        phoneNumberView.setText(patient.getContactInfo().getPhoneNumber());
    }

    private void openRecordsMap() {
        Intent intent = new Intent(this, ViewAllMapLocationsActivity.class);
        startActivity(intent);
    }

    private void openPatientProblemsList() {
        Intent intent = new Intent(this, ProviderPatientViewProblemsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AppStatus.getInstance().setViewedPatient(null);
        super.onBackPressed();
    }
}
