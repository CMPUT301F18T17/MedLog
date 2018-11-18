package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.ProviderController;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.CurrentUser;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider add patient screen, this presents the gui for a
 *         Provider to add a patient they are assigned via their username. From there the Provider
 *         is prompted on further navigation to either add another patient, return to the main menu
 *         or to view their newly added patient..
 * </p>
 * <p>
 *     Issues: <br>
 *         A fragment for the Provider to navigate after adding a patient must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.5
 * @see ProviderMenuActivity
 * @see ProviderPatientProfileActivity
 */
public class ProviderAddPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_add_patient);
        Button addButton = findViewById(R.id.activityProviderAddPatient_AddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizeAddingPatient();
            }
        });
    }

    private void finalizeAddingPatient() {
        EditText usernameField = findViewById(R.id.activityPatientLogin_UsernameEditText);
        String username = usernameField.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this,"No username entered",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean validUsername = true;
        Database db = new Database(this);
        Patient toAdd = null;
        try {
            toAdd = db.loadPatient(username);
        } catch (UserNotFoundException e) {
            validUsername = false;
        }

        if (!validUsername) {
            Toast.makeText(this,"Invalid patient username",Toast.LENGTH_SHORT).show();
            return;
        }

        if (toAdd == null) {
            Toast.makeText(this,"Failed to find patient",Toast.LENGTH_SHORT).show();
            return;
        }

        CareProvider provider = CurrentUser.getInstance().getAsProvider();
        if (provider.getPatients().contains(toAdd)) {
            Toast.makeText(this,"Patient already added",Toast.LENGTH_SHORT).show();
            return;
        }

        ProviderController controller = new ProviderController(this);
        controller.addPatient(provider,toAdd);.

        //TODO Add opening of fragment for post addition navigation.

        //This currently is a stand in for this popup navigation
        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);
        intent.putExtra("PATIENT",toAdd);
        startActivity(intent);
    }


}
