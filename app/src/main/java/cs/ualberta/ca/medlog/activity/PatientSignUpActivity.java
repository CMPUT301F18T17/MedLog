package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The patient sign up screen activity for the Application, this presents the gui for a
 *         new Patient to sign up by entering a username and their contact information in the form
 *         of an email and phone number. With this information added they can then sign up.
 * </p>
 * <p>
 *     Issues: <br>
 *         Need a Patient controller to test if a username is valid
 *         Need a Patient/System controller to add the new Patient to the system.
 *         Need a Patient/System controller to set the input username to be the logged in User.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.2
 * @see PatientLoginActivity
 * @see PatientMenuActivity
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
                performPatientSignup();
            }
        });
    }

    private void performPatientSignup() {
        EditText usernameField = findViewById(R.id.activityPatientSignUp_UsernameEditText);
        String username = usernameField.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(this,"No username entered",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean usernameAvailable = true;    //TODO Set to false once controller added.
        //TODO Check this username using a Patient Controller, if free change the boolean
        if (!usernameAvailable) {
            Toast.makeText(this,"Username already used",Toast.LENGTH_SHORT).show();
            return;
        }

        EditText emailField = findViewById(R.id.activityPatientSignUp_EmailEditText);
        String email = emailField.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this,"No email entered",Toast.LENGTH_SHORT).show();
            return;
        }

        EditText phoneNumberField = findViewById(R.id.activityPatientSignUp_PhoneEditText);
        String phoneNumber = phoneNumberField.getText().toString();
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this,"No phone number entered",Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO Add controller call to add the given Patient to the system

        //TODO Add code contacting the system to inform it that the given Patient is logged in

        Intent intent = new Intent(this, PatientMenuActivity.class);
        startActivity(intent);
    }
}
