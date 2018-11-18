package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ConnectException;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.user.ContactInfo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.CurrentUser;

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


        Database db = new Database(this);
        boolean usernameAvailable = false;

        try {
            usernameAvailable = db.patientUsernameAvailable(username);
        }catch(ConnectException e){
            Toast.makeText(this, "Failed to connect.", Toast.LENGTH_SHORT).show();
            return;
        }


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
        String phoneNumber = phoneNumberField.getText().toString().replace(" ", "");
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this,"No phone number entered",Toast.LENGTH_SHORT).show();
            return;
        }

        ContactInfo contactInfo;
        try{
            contactInfo = new ContactInfo(phoneNumber, email);
        }catch(RuntimeException e){
            Toast.makeText(this, "Invalid email or phone number.", Toast.LENGTH_SHORT).show();
            return;
        }


        Patient patient = new Patient(contactInfo, username);
        if(db.savePatient(patient)){
            CurrentUser.getInstance().set(patient);
        }else{
            Toast.makeText(this, "Try again later", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, PatientMenuActivity.class);
        startActivity(intent);
    }
}
