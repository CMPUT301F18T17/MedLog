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
 *         Actual handling of a logout must be added.
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_provider_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuProviderMain_Logout:
                logoutProvider();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void logoutProvider() {
        //TODO Add code to perform the Care Provider logout

        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }
}
