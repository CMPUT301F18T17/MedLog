package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.security.Provider;
import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Problem;

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
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see ProviderPatientProfileActivity
 * @see ProviderProblemViewActivity
 */
public class ProviderPatientViewProblemsActivity extends AppCompatActivity {
    private String patientUsername;
    private ArrayList<Problem> problems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_patient_view_problems);

        Intent intent = getIntent();
        patientUsername = intent.getStringExtra("PATIENT_USERNAME");
        problems = (ArrayList<Problem>) intent.getSerializableExtra("PROBLEMS");

        ListView problemsListView = findViewById(R.id.activityProviderPatientViewProblems_ProblemsListView);
        problemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openProblemView(position);
            }
        });

        ArrayAdapter<Problem> problemArrayAdapter = new ArrayAdapter<>(this,0,problems);
        problemsListView.setAdapter(problemArrayAdapter);
    }

    private void openProblemView(int index) {
        Intent intent = new Intent(this, ProviderProblemViewActivity.class);
        intent.putExtra("PATIENT_USERNAME",patientUsername)
        intent.putExtra("PROBLEM",problems.get(index));
        startActivity(intent);
    }
}
