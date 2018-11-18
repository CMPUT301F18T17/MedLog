package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.SyncController;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.CurrentUser;

/**
 * <p>
 *     Description: <br>
 *         The patient login screen activity for the Application, this presents the gui for a
 *         Patient to enter their username and proceed to login, or to move to a Patient sign up
 *         screen if they don't have an account.
 * </p>
 * <p>
 *     Issues: <br>
 *         Need a Patient controller to test if a username is valid.
 *         Need a Patient/System controller to set the input username to be the logged in User.
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.2
 * @see StartScreenActivity
 * @see PatientMenuActivity
 * @see PatientSignUpActivity
 */
public class PatientLoginActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "cs.ualberta.ca.medlog.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        Button loginButton = findViewById(R.id.activityPatientLogin_LoginButton);
        Button signUpButton = findViewById(R.id.activityPatientLogin_SignUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performPatientLogin();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientSignUp();
            }
        });
    }

    private void performPatientLogin() {
        EditText usernameField = findViewById(R.id.activityPatientLogin_UsernameEditText);
        String username = usernameField.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this,"No username entered",Toast.LENGTH_SHORT).show();
            return;
        }

        SyncController sc = new SyncController(this);
        try{
            sc.syncPatient(username);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Failed to connect to server",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean validPatient = false;
        Database db = new Database(this);
        Patient toLogin = null;
        try{
            toLogin = db.loadPatient(username);
            if(toLogin != null){
                validPatient = true;
            }
        }catch(Exception e){
            Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT).show();
            return;
        }

        if (validPatient) {
            CurrentUser.getInstance().set(toLogin);

            Intent intent = new Intent(this, PatientMenuActivity.class);
            intent.putExtra(EXTRA_MESSAGE,username);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Invalid username",Toast.LENGTH_SHORT).show();
        }
    }

    private void openPatientSignUp() {
        Intent intent = new Intent(this, PatientSignUpActivity.class);
        startActivity(intent);
    }
}
