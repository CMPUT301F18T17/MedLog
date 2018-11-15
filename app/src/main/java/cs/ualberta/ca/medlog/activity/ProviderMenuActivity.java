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
 *         The care provider main menu screen activity for the Application, this presents the gui
 *         for the Provider to proceed to screens in which they can add new patients, view their
 *         already added patients or search through their patient problems.
 *         An options menu is also present from which the user can logout and return to the start
 *         screen.
 * </p>
 * <p>
 *     Issues: <br>
 *         Contact of the system to inform it that the provider is logged out must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.4
 * @see StartScreenActivity
 * @see ProviderLoginActivity
 * @see ProviderRegistrationActivity
 * @see ProviderAddPatientActivity
 * @see ProviderViewPatientsActivity
 * @see ProviderSearchActivity
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
        Intent intent = new Intent(this, ProviderAddPatientActivity.class);
        startActivity(intent);
    }

    private void openProviderViewPatients() {
        Intent intent = new Intent(this, ProviderViewPatientsActivity.class);
        startActivity(intent);
    }

    private void openProviderSearchProblems() {
        Intent intent = new Intent(this, ProviderSearchActivity.class);
        startActivity(intent);
    }

    private void logoutProvider() {
        //TODO Contact system to inform it the Care Provider is logged out.

        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }
}
