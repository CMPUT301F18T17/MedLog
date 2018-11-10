package cs.ualberta.ca.medlog.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider patient problems list screen, this presents the gui
 *         for a Care Provider to view a list of their patients problems and proceed to a given
 *         one by clicking them.
 *         The Activity for the Patient view problems screen, this presents the gui for a Patient
 *         to view a list of their problems and proceed to a given one by clicking them.
 * </p>
 * <p>
 *     Issues: <br>
 *         Add connection to a Patient problems list to display
 *         Add ability to grab a selected problem
 *         Add transfer to the Provider Problem View Activity
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see ProviderPatientProfileActivity
 */
public class ProviderPatientViewProblemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_patient_view_problems);

        //TODO Add code for an array adapter to connect to a provided problems list

        ListView problemsList = findViewById(R.id.activityProviderPatientViewProblems_ProblemsListView);
        problemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openProblemView(position);
            }
        });
    }

    private void openProblemView(int listIndex) {
        //TODO Add problem grabbing code

        //TODO Add transfer to the Provider Problem View Activity
    }
}
