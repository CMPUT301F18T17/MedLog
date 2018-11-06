package cs.ualberta.ca.medlog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient sign up screen, this presents the gui for a Patient to
 *         enter their new accounts username, email and phone number and proceed to create the
 *         account.
 * </p>
 * <p>
 *     Issues: <br>
 *         Connection to a Patient controller is required to validate the username isn't used.
 *         Transfer to a Patient Main Menu must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see PatientLoginActivity
 */
public class PatientSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);
        Button completeButton = findViewById(R.id.activityPatientSignUp_CompleteButton);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptPatientSignUp();
            }
        });
    }

    private void attemptPatientSignUp() {
        //TODO Connect to a Patient controller to check if the Patient already exists

        //TODO Add transfer to Patient Main Menu Activity
    }
}
