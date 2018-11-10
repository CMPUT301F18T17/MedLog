package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Locale;

import cs.ualberta.ca.medlog.R;

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
 *         Code must be added for assigning the Patient to the Provider in the model.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.2
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
        //TODO Add code for assigning the chosen patient to the provider.

        //TODO Add opening of fragment for post addition navigation.

        Intent intent = new Intent(this, ProviderPatientProfileActivity.class);
        startActivity(intent);
    }


}
