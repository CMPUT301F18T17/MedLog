package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.content.res.Resources;
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
 *         None.
 * </p>
 *
 * <p>
 *     Resources: <br>
 *         Robertas Uldukis, Response to StackOverflow question "How to read value from string.xml in android?"
 *         https://stackoverflow.com/a/20075784
 *         https://stackoverflow.com/users/2343261/robertas-uldukis
 *         Answered 2013-11-09, accessed 2018-11-30
 *
 * </p>
 *
 * @author Tyler Gobran, Tem Tamre
 * @version 1.0
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
            Toast.makeText(this,R.string.activityPatientEnterRegisterCode_NoCode,Toast.LENGTH_SHORT).show();
            return;
        }

        Database db = new Database(this);
        String username;
        try {
            String encryptionKey = "CODE";
            username = Encryption.byteArrayToString(Encryption.decryptData(encryptionKey, code+"=="));
            db.addLoginCode(username);
        } catch (EncryptionException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.activityPatientEnterRegisterCode_CodeUnreadable,Toast.LENGTH_SHORT).show();
            return;
        }

        SyncController sc = new SyncController(this);
        try{
            sc.syncPatient(username);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this,R.string.activityPatientEnterRegisterCode_NoServer,Toast.LENGTH_SHORT).show();
            return;
        }


        Patient toLogin;
        try {
            toLogin = db.loadPatient(username);
        } catch(Exception e){
            Toast.makeText(this, R.string.activityPatientEnterRegisterCode_NoRegister, Toast.LENGTH_SHORT).show();
            return;
        }

        if (toLogin != null) {
            AppStatus.getInstance().setCurrentUser(toLogin);

            db.addLoginCode(username);

            Toast.makeText(this,R.string.activityPatientEnterRegisterCode_Registered,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PatientMenuActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,R.string.activityPatientEnterRegisterCode_NoRegister,Toast.LENGTH_SHORT).show();
        }
    }
}
