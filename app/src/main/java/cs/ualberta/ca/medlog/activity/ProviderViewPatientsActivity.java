package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cs.ualberta.ca.medlog.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_view_patients);

        //TODO Add code for an array adapter to connect to a provided patients list

        ListView patientsList = findViewById(R.id.activityProviderViewPatients_PatientsListView);
        patientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPatientView(position);
            }
        });
    }

    private void openPatientView(int index) {
        //TODO Add patient grabbing code

        //TODO Add transfer to the Patient Profile View Activity
    }
}
