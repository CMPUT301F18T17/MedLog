package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 *         Transfer to a Patient View Problems must be added.
 *         Transfer to a Patient Search Problems must be added.
 *         Transfer to a Patient Profile must be added
 *         Actual handling of a logout must be added.
 *         Initial login body picture dialog must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.2
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

        //TODO Add code for initial login body picture prompts
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_patient_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPatientMain_ViewProfile:
                openPatientProfile();
                return true;
            case R.id.menuPatientMain_Logout:
                logoutPatient();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openPatientAddProblem() {
        Intent intent = new Intent(this, PatientAddProblemActivity.class);
        startActivity(intent);
    }

    private void openPatientViewProblems() {
        //TODO Add transfer to Patient View Problems Activity
    }

    private void openPatientSearchProblems() {
        //TODO Add transfer to Patient Search Problems Activity
    }

    private void openPatientProfile() {
        //TODO Add transfer to the Patient Profile Activity
    }

    private void logoutPatient() {
        //TODO Add code to perform the Patient logout

        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }
}
