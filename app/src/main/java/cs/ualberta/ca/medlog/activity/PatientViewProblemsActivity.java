package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.PatientController;
import cs.ualberta.ca.medlog.entity.Problem;

/**
 * <p>
 *     Description: <br>
 *         The patient view problems screen for the Application, this presents a gui for a patient
 *         to view a list of their problems and proceed to view a given one further by clicking on
 *         them.
 * </p>
 * <p>
 *     Issues: <br>
 *         A call to the system to get the currently logged in patient's problems must be added.
 *         Setting the problems arrayList to what was returned must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.3
 * @see PatientMenuActivity
 * @see PatientProblemViewActivity
 */
public class PatientViewProblemsActivity extends AppCompatActivity {

    private ArrayList<Problem> problems;
    Intent intent=getIntent();
    private String username = intent.getStringExtra(PatientMenuActivity.EXTRA_MESSAGE);
    public final static String EXTRA_MESSAGE = "cs.ualberta.ca.medlog.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_problems);

        // Call to the system to get the logged in patients problems and sets problems ArrayList to the returned patient's problem list
        PatientController controller = new PatientController(this);
        problems=controller.getProblems(username);

        ListView problemsListView = findViewById(R.id.activityPatientViewProblems_ProblemsListView);
        problemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openProblemView(position);
            }
        });

        ArrayAdapter<Problem> problemArrayAdapter = new ArrayAdapter<Problem>(this,0,problems);
        problemsListView.setAdapter(problemArrayAdapter);
    }

    private void openProblemView(int listIndex) {
        Intent intent = new Intent(this, PatientProblemViewActivity.class);
        intent.putExtra("problemIndex",listIndex);
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }
}
