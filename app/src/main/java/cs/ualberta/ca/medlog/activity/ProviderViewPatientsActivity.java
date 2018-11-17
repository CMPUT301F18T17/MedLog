package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.CurrentUser;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider view patients screen, this presents the gui for a
 *         Provider to view a list of their patients and proceed to a given patients profile by
 *         clicking on them.
 * </p>
 * <p>
 *     Issues: <br>
 *         Add connection to a Provider patients list to view.
 *         Add ability to grab a selected patient.
 *         Add transfer to the Patient Profile View Activity.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PatientMenuActivity
 */
public class ProviderViewPatientsActivity extends AppCompatActivity {

    ArrayAdapter<Patient> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_view_patients);

        adapter = new ArrayAdapter<>(this,
                R.layout.list_item, CurrentUser.getInstance().getAsProvider().getPatients());

        ListView patientsList = findViewById(R.id.activityProviderViewPatients_PatientsListView);
        patientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPatientView(position);
            }
        });

        patientsList.setAdapter(adapter);
    }

    private void openPatientView(int index) {
        //TODO Add patient grabbing code

        //TODO Add transfer to the Patient Profile View Activity
    }
}
