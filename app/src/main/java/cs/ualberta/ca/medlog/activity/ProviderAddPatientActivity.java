package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.ProviderController;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

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
        EditText usernameField = findViewById(R.id.activityProviderAddPatient_TitleEditText);
        String username = usernameField.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this,"No username entered",Toast.LENGTH_SHORT).show();
            return;
        }

        Database db = new Database(this);
        Patient newPatient;
        try {
            newPatient = db.loadPatient(username);
        } catch(Exception e){
            Toast.makeText(this,"Invalid patient username",Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPatient == null) {
            Toast.makeText(this,"Failed to find patient",Toast.LENGTH_SHORT).show();
            return;
        }

        CareProvider provider = (CareProvider)AppStatus.getInstance().getCurrentUser();
        if (provider.getPatients().contains(newPatient)) {
            Toast.makeText(this,"Patient already added",Toast.LENGTH_SHORT).show();
            return;
        }

        ProviderController controller = new ProviderController(this);
        controller.addPatient(provider,newPatient);

        //TODO Add opening of fragment for post addition navigation.

        //This currently is a stand in for this popup navigation
        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);
        AppStatus.getInstance().setViewedPatient(newPatient);
        startActivity(intent);
    }


}
