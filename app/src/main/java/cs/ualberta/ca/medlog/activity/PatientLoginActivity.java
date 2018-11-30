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
import cs.ualberta.ca.medlog.controller.SyncController;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.UserNotFoundException;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The patient login screen activity for the Application, this presents the gui for a
 *         Patient to enter their username and proceed to login, or to move to a Patient sign up
 *         screen if they don't have an account.
 *         Patient's can also proceed to a registration screen in order to register their given
 *         phone to their account so that they can login on it.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran, Tem Tamre
 * @version 1.1
 * @see StartScreenActivity
 * @see PatientMenuActivity
 * @see PatientSignUpActivity
 * @see PatientEnterRegisterCodeActivity
 */
public class PatientLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        Button loginButton = findViewById(R.id.activityPatientLogin_LoginButton);
        Button registerButton = findViewById(R.id.activityPatientLogin_RegisterPhoneButton);
        Button signUpButton = findViewById(R.id.activityPatientLogin_SignUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performPatientLogin();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientRegisterPhone();
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
            Toast.makeText(this,R.string.activityPatientLogin_NoUsername,Toast.LENGTH_SHORT).show();
            return;
        }

        Database db = new Database(this);
        if (!db.checkLoginCode(username)) {
            Toast.makeText(this, R.string.activityPatientLogin_NoRegister,Toast.LENGTH_SHORT).show();
            return;
        }

        SyncController sc = new SyncController(this);
        try{
            sc.syncPatient(username);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this,R.string.activityPatientLogin_NoServer,Toast.LENGTH_SHORT).show();
            return;
        }


        Patient toLogin = null;
        try {
            toLogin = db.loadPatient(username);
        } catch(UserNotFoundException e){
            Toast.makeText(this, R.string.activityPatientLogin_NoPatient, Toast.LENGTH_SHORT).show();
            return;
        } catch (ConnectException e) {
            Toast.makeText(this, R.string.activityPatientLogin_NoServer, Toast.LENGTH_SHORT).show();
            return;
        }

        if (toLogin != null) {
            AppStatus.getInstance().setCurrentUser(toLogin);

            Intent intent = new Intent(this, PatientMenuActivity.class);
            Toast.makeText(this,R.string.activityPatientLogin_LoggedIn,Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else {
            Toast.makeText(this,R.string.activityPatientLogin_FailedLogin,Toast.LENGTH_SHORT).show();
        }
    }

    private void openPatientRegisterPhone() {
        Intent intent = new Intent(this, PatientEnterRegisterCodeActivity.class);
        startActivity(intent);
    }

    private void openPatientSignUp() {
        Intent intent = new Intent(this, PatientSignUpActivity.class);
        startActivity(intent);
    }
}
