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
import cs.ualberta.ca.medlog.singleton.CurrentUser;

/**
 * <p>
 *     Description: <br>
 *         The patient main menu screen activity for the Application, this presents the gui
 *         for the Patient to proceed to screens in which they can add new problems, view their
 *         existing problems or search their problems.
 *         An options menu is also present from which the user can proceed to a screen to view
 *         their profile or logout and return back to the start screen.
 *         On the first login of a user an additional popup asking the user if they want to proceed
 *         to a screen to add body pictures is also displayed.
 * </p>
 * <p>
 *     Issues: <br>
 *         Contact of the system to inform it that the patient is logged out must be added.
 *         Creation and handling of a body picture prompt on first login must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.5
 * @see StartScreenActivity
 * @see PatientLoginActivity
 * @see PatientSignUpActivity
 * @see PatientProfileActivity
 * @see PatientAddProblemActivity
 * @see PatientViewProblemsActivity
 * @see PatientSearchActivity
 */
public class PatientMenuActivity extends AppCompatActivity {
    private String username;
    public final static String EXTRA_MESSAGE = "cs.ualberta.ca.medlog.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        intent.getStringExtra(PatientLoginActivity.EXTRA_MESSAGE);
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

        //if (getParent().getLocalClassName() == "PatientSignUpActivity") {
        //    //TODO Add body pictures popup prompt code.
        //}
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
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }

    private void openPatientViewProblems() {
        Intent intent = new Intent(this, PatientViewProblemsActivity.class);
        intent.putExtra(EXTRA_MESSAGE,username);
        startActivity(intent);
    }

    private void openPatientSearchProblems() {
        Intent intent = new Intent(this, PatientSearchActivity.class);
        startActivity(intent);
    }

    private void openPatientProfile() {
        Intent intent = new Intent(this, PatientProfileActivity.class);
        startActivity(intent);
    }

    private void logoutPatient() {
        CurrentUser.getInstance().set(null);

        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }
}
