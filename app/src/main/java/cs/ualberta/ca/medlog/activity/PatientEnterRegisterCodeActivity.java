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
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.exception.EncryptionException;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.helper.Encryption;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for a patient to enter a registration code they are given in order to
 *         register another phone.
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         Need actual code to register the patient to the phone.
 * </p>
 *
 * @author Tyler Gobran, Tem Tamre
 * @version 0.2
 * @see PatientLoginActivity
 * @see PatientMenuActivity
 */
public class PatientEnterRegisterCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_enter_register_code);

        Button registerButton = findViewById(R.id.activityPatientEnterRegisterCode_RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPhoneToPatient();
            }
        });
    }

    private void registerPhoneToPatient() {
        EditText codeField = findViewById(R.id.activityPatientEnterRegisterCode_CodeEditText);
        String code = codeField.getText().toString();

        if (code.isEmpty()) {
            Toast.makeText(this,"No code entered",Toast.LENGTH_SHORT).show();
            return;
        }

        Database db = new Database(this);
        String username;
        try {
            username = Encryption.byteArrayToString(Encryption.decryptData("CODE", code));
            db.addLoginCode(username);
        } catch (EncryptionException e) {
            e.printStackTrace();
            Toast.makeText(this,"Couldn't read code.",Toast.LENGTH_SHORT).show();
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


        Patient toLogin;
        try {
            toLogin = db.loadPatient(username);
        } catch(Exception e){
            Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show();
            return;
        }

        if (toLogin != null) {
            AppStatus.getInstance().setCurrentUser(toLogin);

            Toast.makeText(this,"Phone Registered",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PatientMenuActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Failed to register",Toast.LENGTH_SHORT).show();
        }
    }
}
