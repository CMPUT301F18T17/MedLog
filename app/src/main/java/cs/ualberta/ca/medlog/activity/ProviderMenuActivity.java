package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider main menu screen, this presents the gui for the
 *         Provider to proceed to screens to add a patient, view their patients or search their
 *         patients problems.
 *         Additionally there is an options menu from which the user can logout.
 * </p>
 * <p>
 *     Issues: <br>
 *         Transfer to a Care Provider Add Patient must be added.
 *         Transfer to a Care Provider View Patients must be added.
 *         Transfer to a Care Provider Search Problems must be added.
 *         Options menu must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see StartScreenActivity
 * @see ProviderLoginActivity
 * @see ProviderRegistrationActivity
 */
public class ProviderMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_menu);
        Button addPatientButton = findViewById(R.id.activityProviderMenu_AddPatientButton);
        Button viewPatientsButton = findViewById(R.id.activityProviderMenu_ViewPatientsButton);
        Button searchProblemsButton = findViewById(R.id.activityProviderMenu_SearchProblemsButton);
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProviderAddPatient();
            }
        });
        viewPatientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProviderViewPatients();
            }
        });
        searchProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProviderSearchProblems();
            }
        });

        //TODO Add code for Care Provider options menu
    }

    private void openProviderAddPatient() {
        //TODO Add transfer to Care Provider Add Patient Activity
    }

    private void openProviderViewPatients() {
        //TODO Add transfer to Care Provider View Patients Activity
    }

    private void openProviderSearchProblems() {
        //TODO Add transfer to Care Provider Search Problems Activity
    }
}
