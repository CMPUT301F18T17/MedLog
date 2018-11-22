package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

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
    private ArrayList<Problem> problems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_patient_view_problems);

        problems = AppStatus.getInstance().getViewedPatient().getProblems();

        ListView problemsListView = findViewById(R.id.activityProviderPatientViewProblems_ProblemsListView);
        problemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openProblemView(position);
            }
        });

        ProblemAdapter problemArrayAdapter = new ProblemAdapter(this,problems);
        problemsListView.setAdapter(problemArrayAdapter);
    }

    private void openProblemView(int index) {
        Intent intent = new Intent(this, ProviderProblemViewActivity.class);
        AppStatus.getInstance().setViewedProblem(problems.get(index));
        startActivity(intent);
    }
}
