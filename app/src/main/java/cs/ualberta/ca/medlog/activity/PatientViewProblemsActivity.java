package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The patient view problems screen for the Application, this presents a gui for a patient
 *         to view a list of their problems and proceed to view a given one further by clicking on
 *         them.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see PatientMenuActivity
 * @see PatientProblemViewActivity
 */
public class PatientViewProblemsActivity extends AppCompatActivity {
    private ArrayList<Problem> problems;
    private ProblemAdapter problemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_problems);

        problems = ((Patient)AppStatus.getInstance().getCurrentUser()).getProblems();

        ListView problemsListView = findViewById(R.id.activityPatientViewProblems_ProblemsListView);
        problemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openProblemView(position);
            }
        });

        problemArrayAdapter = new ProblemAdapter(this,problems);
        problemsListView.setAdapter(problemArrayAdapter);
    }

    @Override
    protected void onStart() {
        problemArrayAdapter.notifyDataSetChanged();
        super.onStart();
    }

    private void openProblemView(int index) {
        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        AppStatus.getInstance().setViewedProblem(problems.get(index));
        startActivity(intent);
    }
}
