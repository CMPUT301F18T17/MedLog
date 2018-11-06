package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Patient login screen, this presents the gui for a Patient
 *         to enter their username and login, or to proceed to a sign up screen.
 * </p>
 * <p>
 *     Issues: <br>
 *         Connection to a Patient controller is required to validate their username.
 *         Transfer to a Patient Main Menu must be added.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see StartScreenActivity
 * @see PatientSignUpActivity
 */
public class PatientLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        Button loginButton = findViewById(R.id.activityPatientLogin_LoginButton);
        Button signUpButton = findViewById(R.id.activityPatientLogin_SignUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptPatientLogin();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientSignUp();
            }
        });
    }

    private void attemptPatientLogin() {
        //TODO Connect to a Patient controller to check if the Patient exists

        //TODO Add transfer to Patient Main Menu Activity
    }

    private void openPatientSignUp() {
        Intent intent = new Intent(this, PatientSignUpActivity.class);
        startActivity(intent);
    }
}
