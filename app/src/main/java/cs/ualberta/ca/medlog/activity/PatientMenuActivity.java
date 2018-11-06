package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient main menu screen, this presents the gui for the Patient
 *         to proceed to screens to add a problem, view their problems or search their problems.
 *         Additionally there is an options menu from which the user can view their profile or
 *         logout from the application.
 *         Finally on the first login of a user an additional body pictures dialog is presented
 *         for the user to add body pictures to their account for use in body locations in records.
 * </p>
 * <p>
 *     Issues: <br>
 *         Transfer to a Patient Add Problem must be added.
 *         Transfer to a Patient View Problems must be added.
 *         Transfer to a Patient Search Problems must be added.
 *         Options menu must be added.
 *         Initial login body picture dialog must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see StartScreenActivity
 * @see PatientLoginActivity
 * @see PatientSignUpActivity
 */
public class PatientMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_menu);
        Button addProblemButton = findViewById(R.id.activityPatientMenu_AddProblemButton);
        Button viewProblemsButton = findViewById(R.id.activityPatientMenu_ViewProblemsButton);
        Button searchProblemsButton = findViewById(R.id.activityPatientMenu_SearchProblemsButton);
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientAddProblem();
            }
        });
        viewProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientViewProblems();
            }
        });
        searchProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientSearchProblems();
            }
        });

        //TODO Add code for Patient options menu

        //TODO Add code for initial login body picture prompts
    }

    private void openPatientAddProblem() {
        //TODO Add transfer to Patient Add Problem Activity
    }

    private void openPatientViewProblems() {
        //TODO Add transfer to Patient View Problems Activity
    }

    private void openPatientSearchProblems() {
        //TODO Add transfer to Patient Search Problems Activity
    }
}
