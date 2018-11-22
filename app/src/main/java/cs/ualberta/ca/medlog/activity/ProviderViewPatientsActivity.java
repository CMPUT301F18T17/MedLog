package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider view patients screen, this presents the gui for a
 *         Provider to view a list of their patients and proceed to a given patients profile by
 *         clicking on them.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see ProviderMenuActivity
 * @see ProviderPatientProfileActivity
 */
public class ProviderViewPatientsActivity extends AppCompatActivity {
    private ArrayList<Patient> patients;
    private PatientAdapter patientArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_view_patients);

        patients = ((CareProvider)AppStatus.getInstance().getCurrentUser()).getPatients();



        ListView patientsList = findViewById(R.id.activityProviderViewPatients_PatientsListView);
        patientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPatientView(position);
            }
        });

        patientArrayAdapter = new PatientAdapter(this, patients);
        patientsList.setAdapter(patientArrayAdapter);
    }

    @Override
    protected void onStart() {
        patientArrayAdapter.notifyDataSetChanged();
        super.onStart();
    }

    private void openPatientView(int index) {
        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);
        AppStatus.getInstance().setViewedPatient(patients.get(index));
        startActivity(intent);
    }
}
